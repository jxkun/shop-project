package com.lunarku.content.service;

import java.util.List;

import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbContentCategory;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCatgory(Long parentId);
	
	ResponseResult addContentCatgory(long parentId, String name);
	
	ResponseResult updateContentCatgory(long id, String name);
	
	ResponseResult deleteContentCatgory(long id);
}
