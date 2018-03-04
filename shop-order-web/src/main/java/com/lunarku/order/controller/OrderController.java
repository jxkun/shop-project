package com.lunarku.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lunarku.shop.common.util.CookieUtils;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.pojo.TbItem;

@Controller
public class OrderController {
	
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		// 取用户id
		// 从cookie中获取token, 根据token查询用户
		// 根据用户id查询收货地址
		// 从cookie中取商品列表
		List<TbItem> itemList = getCartItemList(request);
		// 传输得到页面
		request.setAttribute("cartList", itemList);
		// 返回逻辑视图
		return "order-cart";
	}
	
	private List<TbItem> getCartItemList(HttpServletRequest request) {
		//从cookie中取购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_COOKIE, true);
		if (StringUtils.isBlank(json)) {
			//如果没有内容，返回一个空的列表
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
}
