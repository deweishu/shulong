package com.qkl.apk.service;


import com.qkl.apk.cache.BannerCache;
import com.qkl.apk.dto.BannerDto;
import com.qkl.apk.entity.Activity;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.apk.jpa.BannerRepository;
import com.qkl.apk.dto.BannerReq;
import com.qkl.apk.assembler.BannerAssembler;
import com.qkl.apk.entity.Banner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;
import com.google.common.collect.Lists;
import com.qkl.common.util.StringUtil;
import javax.persistence.criteria.*;



/**
*
generate by dengjihai
*/
@Service
public class BannerService {

	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	BannerCache bannerCache;

	@Autowired
	ApkRepository apkRepository;

	@Transactional(rollbackFor = Exception.class)
	public String save(BannerDto bannerDto){
		Banner  banner;
		if (StringUtil.isNotNil(bannerDto.getId())) {
			banner=bannerRepository.findOne(bannerDto.getId());
			Assert.notNull(banner, "不存该数据");
			banner = BannerAssembler.convertToEntity(bannerDto, banner);
		}else{
			banner =BannerAssembler.convertToEntity(bannerDto,null);
		}

		// apk id 不为空，就查询出app的详情，插入到数据库
		if(StringUtil.isNotNil(bannerDto.getApkId())){
			String apkId=bannerDto.getApkId();
			Apk apk=apkRepository.findOne(apkId);
			Assert.notNull(apk,"不存在该应用信息");
			banner.setApk(apk);
		}

		String id =bannerRepository.save(banner).getId();
		bannerCache.delete();
		return id;
	}


	public BannerDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return BannerAssembler.convertToDto(bannerRepository.findOne(id));
	}

	/**
	 * 删除banner
	 * @param id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id){
		bannerRepository.delete(id);
		bannerCache.delete();
	}


	/**
	 * 修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	public String updateStatus(Boolean status,String id){
		Banner banner=bannerRepository.findOne(id);
		Assert.notNull(banner,"不存在该BANNER信息");
		banner.setStatus(status);
		String string=bannerRepository.save(banner).getId();
		bannerCache.delete();
		return string;

	}

	public Page<BannerDto> findPage(BannerReq bannerReq) {
		PageRequest pageRequest = new PageRequest(bannerReq.getPageNumber(), bannerReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Banner> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if (StringUtil.isNotBlank(bannerReq.getQueryLike())) {
				predicates.add(builder.equal(root.get("bannerTitle"), bannerReq.getQueryLike()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Banner> bannerPage = bannerRepository.findAll(spec,pageRequest);
		Page<BannerDto> bannerDtoPage = new PageImpl<>(BannerAssembler.convertToDtoList(bannerPage.getContent()), pageRequest,bannerPage.getTotalElements());
		return bannerDtoPage;
	}


	/**
	 * 获取banner信息
	 * @return
	 */
	public List<BannerDto> getBannerList(){
		List<BannerDto> bannerDtoList=bannerCache.getBannerList();
		if(bannerDtoList!=null && bannerDtoList.size()>0){
			return bannerDtoList;
		}
		List<Banner> bannerList=bannerRepository.findByStatusOrderByCreateTimeDesc(true);
		List<BannerDto> bannerDtoList1=BannerAssembler.convertToDtoList(bannerList);
		bannerCache.save(bannerDtoList1);
		return bannerDtoList1;
	}
}
