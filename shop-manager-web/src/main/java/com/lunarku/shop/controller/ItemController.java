package com.lunarku.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getIbItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
}
