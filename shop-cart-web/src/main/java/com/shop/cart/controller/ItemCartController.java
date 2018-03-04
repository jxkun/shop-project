package com.shop.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.common.util.CookieUtils;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.service.ItemService;

@Controller
public class ItemCartController {

	@Autowired
	private ItemService itemService;
	
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	@Value("${CART_EXPIER}")
	private Integer CART_EXPIER;
	
	/**
	 * 向购物车添加商品
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,
			@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {	
		// 先获取cookie中商品的列表
		List<TbItem> itemList = getCartItemList(request);
		// 判断商品列表中商品是存在,若不为空则在商品列表中添加商品,若不为空,则增加商品的数量
		boolean flag = false;
		for(TbItem item : itemList) {
			if(item.getId() == itemId.longValue()) {
				// 如果商品存在，则商品数量增加
				item.setNum(item.getNum() + num);
				break;
			}
		}
		
		// 如果商品在之前购物车中不存在，重新添加
		if(!flag) {
			TbItem newItem = itemService.getItemById(itemId);
			// 设置数量
			newItem.setNum(num);
			// 取第一张图片
			String image = newItem.getImage();
			if(StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				newItem.setImage(images[0]);
			}
			itemList.add(newItem);
		}
		
		// 将购物车中信息重新写入cookie
		CookieUtils.setCookie(request, response, CART_COOKIE, 
				JsonUtils.objectToJson(itemList), CART_EXPIER, true);
		return "cartSuccess";
	}
	
	/**
	 * 获取cookie中存储的购物车商品信息
	 * @param request
	 * @return
	 */
	public List<TbItem> getCartItemList(HttpServletRequest request){
		// 从cookie中获取购物车信息
		String cookieValue = CookieUtils.getCookieValue(request, CART_COOKIE, true);
		// 若cookieValue为空，返回一个空的列表
		if(StringUtils.isBlank(cookieValue)) {
			return new ArrayList<TbItem>();
		}
		List<TbItem> itemList = JsonUtils.jsonToList(cookieValue, TbItem.class);
		return itemList;
	}
	
	/**
	 * 显示购物车商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request) {
		List<TbItem> itemList = getCartItemList(request);
		// 将购物车商品列表数据转发给jsp
		request.setAttribute("cartList", itemList);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public ResponseResult updateItemNum(@PathVariable Long itemId, 
			@PathVariable Integer num, 
			HttpServletRequest request,
			HttpServletResponse response) {
		// 先获取cookie中购物车列表信息
		List<TbItem> itemList = getCartItemList(request);
		// 修改对应商品数量
		for(TbItem item : itemList) {
			if(item.getId() == itemId.longValue()) {
				item.setNum(num);
				break;
			}
		}
		// 写入到cookie
		CookieUtils.setCookie(request, response, CART_COOKIE, 
				JsonUtils.objectToJson(itemList), CART_EXPIER, true);
		// 返回成功
		return ResponseResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, 
			HttpServletRequest request, HttpServletResponse response) {
		// congcookie中获取商品列表
		List<TbItem> itemList = getCartItemList(request);
		// 删除对应商品id的商品
		for(TbItem item : itemList) {
			if(item.getId() == itemId.longValue()) {
				itemList.remove(item);
				break;
			}
		}
		// 写入cookie
		CookieUtils.setCookie(request, response, CART_COOKIE, 
				JsonUtils.objectToJson(itemList), CART_EXPIER, true);
		// 重定向到cart展示页面
		return "redirect:/cart/cart.html";
	}
}
