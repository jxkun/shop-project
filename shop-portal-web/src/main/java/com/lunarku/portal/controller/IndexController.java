package com.lunarku.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lunarku.content.service.ContentService;
import com.lunarku.portal.pojo.BigADNode;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.pojo.TbContent;

@Controller
public class IndexController {

	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index.html")
	public String showIndex(Model model) {
		List<TbContent> list = contentService.getContentByCid(AD1_CATEGORY_ID);
		
		List<BigADNode> result = new ArrayList<BigADNode>();
		for(TbContent content : list) {
			BigADNode node = new BigADNode();
			node.setAlt(content.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setHref(content.getUrl());
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());
			
			result.add(node);
		}
		
		String adjson = JsonUtils.objectToJson(result);
		model.addAttribute("ad1", adjson);
		return "index";
	}
}
