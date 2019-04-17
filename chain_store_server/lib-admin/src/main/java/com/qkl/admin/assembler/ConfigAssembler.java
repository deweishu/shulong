package com.qkl.admin.assembler;


import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.entity.Config;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class ConfigAssembler {

	public static Config convertToEntity(ConfigDto configDto, Config config){
		if(config==null){
			config = new Config();
		}
		config.setConfigCode(configDto.getConfigCode());
		config.setConfigDesc(configDto.getConfigDesc());
		config.setConfigContent(configDto.getConfigContent());
		config.setConfigNum(configDto.getConfigNum());
		return config;
	}


	public static ConfigDto convertToDto(Config config){
		if(config==null){
			return null;
		}
		ConfigDto configDto = new ConfigDto();
		configDto.setId(config.getId());
		configDto.setConfigCode(config.getConfigCode());
		configDto.setConfigDesc(config.getConfigDesc());
		configDto.setConfigContent(config.getConfigContent());
		configDto.setConfigNum(config.getConfigNum());
		configDto.setCreateTime(config.getCreateTime());
		configDto.setUpdateTime(config.getUpdateTime());
		return configDto;
	}


	public static List<ConfigDto> convertToDtoList(List<Config> configList){
		List<ConfigDto> configDtoList= new ArrayList<>();
		configList.forEach(config -> configDtoList.add(convertToDto(config)));
		return configDtoList;
	}
}
