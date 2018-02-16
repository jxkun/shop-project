package com.lunarku.content.service;

import java.util.List;

import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbContent;

public interface ContentService {
	
	EasyUIDataResult selectContent(long categoryId, int page, int rows);
	
	ResponseResult addContent(TbContent content);
	
	ResponseResult updateContent(TbContent content);
	
	TbContent selsectContent(long id);
	
	ResponseResult deleteContent(long id);
	
	ResponseResult deleteContents(long[] ids);
	
	List<TbContent> getContentByCid(long cid);
}
