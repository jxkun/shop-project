package com.lunarku.order.service;

import com.lunarku.order.pojo.OrderInfo;
import com.lunarku.shop.common.util.ResponseResult;

public interface OrderService {
	ResponseResult ccreateOrder(OrderInfo orderInfo);
}
