package com.qkl.apk.assembler;


import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.entity.Img;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class ImgAssembler {

	public static Img convertToEntity(ImgDto imgDto, Img img){
		if(img==null){
			img = new Img();
		}
		img.setId(imgDto.getId());
		img.setImgUrl(imgDto.getImgUrl());
		return img;
	}


	public static ImgDto convertToDto(Img img){
		if(img==null){
			return null;
		}
		ImgDto imgDto = new ImgDto();
		imgDto.setId(img.getId());
		imgDto.setImgUrl(img.getImgUrl());
		imgDto.setApkId(img.getApk()==null?null:img.getApk().getId());
		imgDto.setCreateTime(img.getCreateTime());
		imgDto.setUpdateTime(img.getUpdateTime());
		imgDto.setImgSort(img.getImgSort());
		return imgDto;
	}


	public static List<ImgDto> convertToDtoList(List<Img> imgList){
		List<ImgDto> imgDtoList= new ArrayList<>();
		imgList.forEach(img -> imgDtoList.add(convertToDto(img)));
		return imgDtoList;
	}
}
