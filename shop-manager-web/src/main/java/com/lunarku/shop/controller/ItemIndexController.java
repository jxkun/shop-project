package com.lunarku.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.search.service.SearchItemService;
import com.lunarku.shop.common.util.ResponseResult;

@Controller
public class ItemIndexController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public ResponseResult  importItemIndex() {
		ResponseResult result = searchItemService.updateItemIndex();
		return result;
	}
}
