package com.lunarku.shop.service;

import java.util.List;

import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	
	EasyUIDataResult getItemList(int page, int rows);
	
	ResponseResult addItem(TbItem item, String desc);
		
	boolean deleteByIds(Long[] ids);
	
	boolean setStatus(Long[] ids, byte status);
	
	boolean updateItem(TbItem item);
} 
