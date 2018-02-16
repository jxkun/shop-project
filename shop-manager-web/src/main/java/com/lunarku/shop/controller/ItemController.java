package com.lunarku.shop.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.common.util.StatusCode;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.service.ItemDescService;
import com.lunarku.shop.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	/**
	 * 回显商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIDataResult getTbItemList(int page, int rows) {
		EasyUIDataResult result = itemService.getItemList(page, rows);
		return result;
	}
	/**
	 * 添加商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseResult saveItem(TbItem item, String desc) {
		ResponseResult  result = itemService.addItem(item, desc);
		return result;
	}
	/**
	 * 批量删除商品
	 * @param params
	 * @return
	 */
	@RequestMapping(value= "/rest/item/delete")
	@ResponseBody
	public ResponseResult deleteItem(Long[] ids) {
		if(ids == null || ids.length == 0)
			return ResponseResult.build(StatusCode.PARAMETER_ERROR.getCode(), StatusCode.PARAMETER_ERROR.getMsg(), null);
		boolean bool = itemService.deleteByIds(ids);
		if(bool) {
			return ResponseResult.ok();
		}
		return ResponseResult.build(StatusCode.PARAMETER_ERROR.getCode(), StatusCode.PARAMETER_ERROR.getMsg(), null);
	}
	
	/**
	 * 商品批量上下架
	 * @param opration
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/rest/item/{opration}")
	@ResponseBody
	public ResponseResult upOrDowmItem(@PathVariable(value="opration") String opration, Long[] ids) {
		if(ids == null ||ids.length == 0) {
			return ResponseResult.build(StatusCode.PARAMETER_ERROR.getCode(), StatusCode.PARAMETER_ERROR.getMsg(), null);
		}
		
		boolean result = false;
		if("reshelf".equals(opration)) {
			result = itemService.setStatus(ids, (byte)1); //上架
		}else if("instock".equals(opration)) {
			result = itemService.setStatus(ids, (byte)2); // 下架
		}
		
		if(result == false) {
			return ResponseResult.build(StatusCode.PARAMETER_ERROR.getCode(), StatusCode.PARAMETER_ERROR.getMsg(), null);
		}
		return ResponseResult.ok();
	}
	
	/**
	 * 异步加载商品
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rest/param/item/query/{id}")
	@ResponseBody
	public String restQueryItemById(@PathVariable(value = "id") long id) {
		TbItem item = itemService.getItemById(id);
		String json = JsonUtils.objectToJson(item);
		return json;
	}
	
	/**
	 * 异步加载商品描述
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rest/query/item/desc/{id}")
	@ResponseBody
	public ResponseResult restQueryItemDesc(@PathVariable(value = "id") long id) {
		TbItemDesc itemDesc = itemDescService.getItemDescById(id);
		return ResponseResult.build(StatusCode.SUSSESS.getCode()
				, StatusCode.SUSSESS.getMsg(), itemDesc);
	}
	/**
	 * 更新商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/rest/update")
	@ResponseBody
	public ResponseResult updateItem(TbItem item, String desc) {
		long preid = item.getId();
		TbItem preItem = itemService.getItemById(preid);
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(preItem.getCreated());
		item.setStatus((byte) 1);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(preItem.getCreated());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		
		boolean itemResult = itemService.updateItem(item);
		boolean descResult = itemDescService.updateItemDesc(itemDesc);
		
		if(itemResult && descResult) {
			return ResponseResult.ok();
		}
		
		return ResponseResult.build(StatusCode.FAIL.getCode(), StatusCode.FAIL.getMsg(), null);
	}

}
