package com.lunarku.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.shop.mapper.TbItemMapper;
import com.lunarku.shop.pojo.EasyUIDataResult;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.service.ItemService;

/**
 *  商品管理服务
 */
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem ibItem = itemMapper.selectByPrimaryKey(itemId);
		return ibItem;
	}


	public EasyUIDataResult getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

}
