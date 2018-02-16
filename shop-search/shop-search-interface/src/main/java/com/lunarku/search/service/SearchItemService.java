package com.lunarku.search.service;

import com.lunarku.shop.common.util.ResponseResult;

public interface SearchItemService {
	
	ResponseResult importItemIndex();
	
	ResponseResult updateItemIndex();
}
