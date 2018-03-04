package com.lunarku.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lunarku.item.pojo.Item;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.service.ItemDescService;
import com.lunarku.shop.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable(value="itemId") Long itemId, Model model) {
		// 根据商品id获取商品信息
		TbItem tbItem = itemService.getItemById(itemId);
		// 将图片拆分
		Item item = new Item(tbItem);
		// 根据商品id获取商品描述
		TbItemDesc tbItemDesc = itemDescService.getItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		return "item";
	}
}
