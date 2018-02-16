package com.lunarku.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.common.util.ResponseResult;

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping("/{pageName}")
	public String getPage(@PathVariable String pageName) {
		return pageName;
	}
	
	@RequestMapping(value = "/rest/page/item-edit")
	public String editItem() {
		return "item-edit";
	}
}
