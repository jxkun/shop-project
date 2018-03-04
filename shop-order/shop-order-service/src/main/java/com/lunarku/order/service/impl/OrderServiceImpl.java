package com.lunarku.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lunarku.order.pojo.OrderInfo;
import com.lunarku.order.service.OrderService;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbOrderItemMapper;
import com.lunarku.shop.mapper.TbOrderMapper;
import com.lunarku.shop.mapper.TbOrderShippingMapper;
import com.lunarku.shop.pojo.TbOrderItem;
import com.lunarku.shop.pojo.TbOrderShipping;
import com.lunarku.shop.redis.JedisClient;

public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private JedisClient jedisClient;
	
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY; 
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	@Override
	public ResponseResult ccreateOrder(OrderInfo orderInfo) {
		// 生成订单单号
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			// 设置初始值
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		// 将订单插入表中,在此之前补全pojo属性
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setCreateTime(date);
		orderMapper.insert(orderInfo);
		
		// 向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for(TbOrderItem orderItem : orderItems) {
			String orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			orderItem.setId(orderItemId);
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		
		// 想订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		// 返回订单号
		return null;
	}

}
