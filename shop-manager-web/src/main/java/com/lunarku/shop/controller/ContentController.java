package com.lunarku.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.content.service.ContentService;
import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 查询分类下的content
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataResult showContentList(Long categoryId, Integer page, Integer rows ) {
		if(categoryId == null || page == null || rows == null) return null;
		EasyUIDataResult result = contentService.selectContent(categoryId, page, rows);
		return result;
	}
	
	/**
	 * 根据ids删除content
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public ResponseResult showContent(long[] ids) {
		ResponseResult result = contentService.deleteContents(ids);
		return result;
	}
	
	/**
	 * 添加content
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public ResponseResult addContent(TbContent content) {
		ResponseResult result = contentService.addContent(content);
		return result;
	}
	
	
	/**
	 * 修改content                      
	 * @param content
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public ResponseResult updateContent(TbContent content) {
		ResponseResult result = contentService.updateContent(content);
		return result;
	}
	
}
