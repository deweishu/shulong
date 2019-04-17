package com.qkl.admin.service;


import com.qkl.admin.cache.ConfigCache;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.jpa.ConfigRepository;
import com.qkl.admin.dto.ConfigReq;
import com.qkl.admin.assembler.ConfigAssembler;
import com.qkl.admin.entity.Config;
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
public class ConfigService {

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	ConfigCache configCache;


	/**
	 * 批量获取配置信息
	 * @return
	 */
	public List<ConfigDto> findAllConfig(){
		List<Config> configList=configRepository.findAll();
		configList.forEach(config -> getConfigByKey(config.getConfigCode()));
		return ConfigAssembler.convertToDtoList(configList);
	}


	/**
	 * 根据key获取配置想信息
	 * @param key
	 * @return
	 */
	public ConfigDto getConfigByKey(String key){
		ConfigDto configDto=configCache.getSysConfig(key);
		if(configDto!=null){
			return configDto;
		}
		Config config=configRepository.findByConfigCode(key);
		if(config==null){
			return null;
		}
		ConfigDto configDto1 = ConfigAssembler.convertToDto(config);
		configCache.save(key,configDto1);
		return configDto1;
	}




	@Transactional(rollbackFor = Exception.class)
	public String save(ConfigDto configDto){
		Config  config;
		if (StringUtil.isNotNil(configDto.getId())) {
			config=configRepository.findOne(configDto.getId());
			Assert.notNull(config, "不存该数据");
			config = ConfigAssembler.convertToEntity(configDto, config);
		}else{
			config =ConfigAssembler.convertToEntity(configDto,null);
		}
		//清除缓存
		configCache.remove(configDto.getConfigCode());
		return configRepository.save(config).getId();
	}


	public ConfigDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return ConfigAssembler.convertToDto(configRepository.findOne(id));
	}


	public Page<ConfigDto> findPage(ConfigReq configReq) {
		PageRequest pageRequest = new PageRequest(configReq.getPageNumber(), configReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Config> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();

			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Config> configPage = configRepository.findAll(spec,pageRequest);
		Page<ConfigDto> configDtoPage = new PageImpl<>(ConfigAssembler.convertToDtoList(configPage.getContent()), pageRequest,configPage.getTotalElements());
		return configDtoPage;
	}
}
