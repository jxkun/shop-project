package com.lunarku.shop.service;

import com.lunarku.shop.pojo.EasyUIDataResult;
import com.lunarku.shop.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	
	EasyUIDataResult getItemList(int page, int rows);
}
