package com.lunarku.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.content.service.ContentCategoryService;
import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.common.util.ResponseResult;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> showContentCategory(
			@RequestParam(value="id", defaultValue="0") long parentId){
		List<EasyUITreeNode> result = categoryService.getContentCatgory(parentId);
		return result;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public ResponseResult createContentCategory(long parentId, String name) {
		ResponseResult result = categoryService.addContentCatgory(parentId, name);
		return result;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public ResponseResult updateContentCategory(long id, String name) {
		ResponseResult result = categoryService.updateContentCatgory(id, name);
		return result;
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public ResponseResult deleteContentCategory(Long parentId, Long id) {
		ResponseResult result = categoryService.deleteContentCatgory(id);
		
		return result;
	}
	
}
