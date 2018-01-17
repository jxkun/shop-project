package com.lunarku.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> showItemCatList(
			@RequestParam(value="id",defaultValue="0") long parentId){
		return itemCatService.getItemCatList(parentId);
	}
	
}
