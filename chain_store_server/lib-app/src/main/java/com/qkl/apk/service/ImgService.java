package com.qkl.apk.service;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.apk.cache.ApkImgCache;
import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.jpa.ApkRepository;
import com.qkl.apk.jpa.ImgRepository;
import com.qkl.apk.dto.ImgReq;
import com.qkl.apk.assembler.ImgAssembler;
import com.qkl.apk.entity.Img;
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
public class ImgService {

	@Autowired
	private ImgRepository imgRepository;

	@Autowired
	ApkRepository apkRepository;

	@Autowired
	ApkImgCache apkImgCache;


	@Autowired
	ConfigService configService;

	@Transactional(rollbackFor = Exception.class)
	public String save(ImgDto imgDto){
		Img  img;
		if (StringUtil.isNotNil(imgDto.getId())) {
			img=imgRepository.findOne(imgDto.getId());
			Assert.notNull(img, "不存该数据");
			img = ImgAssembler.convertToEntity(imgDto, img);
		}else{
			img =ImgAssembler.convertToEntity(imgDto,null);
		}
		Apk apk=apkRepository.findOne(imgDto.getApkId());
		Assert.notNull(apk,"不存在该应用信息");
		img.setApk(apk);
		String id=imgRepository.save(img).getId();
		apkImgCache.remove(imgDto.getApkId());
		return id;
	}


	/**
	 * 统计出apk目前有几张演示图
	 * @param apkId
	 * @return
	 */
	public Integer getImgNumByApk(String apkId){
		Apk apk=apkRepository.findOne(apkId);
		Assert.notNull(apk,"不存在该应用信息");
		return imgRepository.countByApk(apk);
	}

	/**
	 * 判断客户是还能进行添加应用演示图
	 * @param apkId
	 * @return
	 */
	public Boolean validateAddImg(String apkId){
		ConfigDto configDto=configService.getConfigByKey(SysConfigKey.APP_IMG_MAX_NUM.getCode());
		Integer imgNum=getImgNumByApk(apkId);
		if(imgNum>=configDto.getConfigNum()){
			return false;
		}
		return true;
	}


	public ImgDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return ImgAssembler.convertToDto(imgRepository.findOne(id));
	}


	public Page<ImgDto> findPage(ImgReq imgReq) {
		PageRequest pageRequest = new PageRequest(imgReq.getPageNumber(), imgReq.getPageSize(), new Sort(Sort.Direction.ASC, "imgSort"));
		Specification<Img> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(imgReq.getApkId())){
				predicates.add(builder.equal(root.join("apk").get("id").as(String.class),imgReq.getApkId()));
			}
			query.orderBy(builder.asc(root.get("imgSort")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Img> imgPage = imgRepository.findAll(spec,pageRequest);
		Page<ImgDto> imgDtoPage = new PageImpl<>(ImgAssembler.convertToDtoList(imgPage.getContent()), pageRequest,imgPage.getTotalElements());
		return imgDtoPage;
	}


	/**
	 * 根据apkid获取其演示图
	 * @param apkId
	 * @return
	 */
	public List<ImgDto> getAllImgByApkId(String apkId){
		List<ImgDto> imgDtoList=apkImgCache.getImgList(apkId);
		if(imgDtoList!=null && imgDtoList.size()>0){
			return imgDtoList;
		}
		Apk apk=apkRepository.findOne(apkId);
		List<Img> imgs=imgRepository.findByApkOrderByImgSortAsc(apk);
		imgDtoList=ImgAssembler.convertToDtoList(imgs);
		apkImgCache.save(apkId,imgDtoList);
		return imgDtoList;
	}


	@Transactional(rollbackFor = Exception.class)
	public String updateSort(String imgId,Integer sortNum){
		Img img=imgRepository.findOne(imgId);
		Assert.notNull(img,"不存在该图片信息");
		img.setImgSort(sortNum);
		String id= imgRepository.save(img).getId();
		apkImgCache.remove(img.getApk().getId());
		return id;
	}


	/**
	 * 删除图片
	 * @param ids 图片id
	 */
	public void deleteImg(List<String> ids){
		List<Img> imgList=imgRepository.findByIdIn(ids);
		imgRepository.deleteInBatch(imgList);
		if(imgList.size()>0){
			String apkId=imgList.get(0).getApk().getId();
			apkImgCache.remove(apkId);
		}
	}

}
