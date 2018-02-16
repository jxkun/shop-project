package com.lunarku.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.shop.mapper.TbItemDescMapper;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	public TbItemDesc getItemDescById(long id) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		return itemDesc;
	}

	@Override
	public boolean updateItemDesc(TbItemDesc itemDescs) {
		int count = itemDescMapper.updateByPrimaryKey(itemDescs);
		return true;
	}

}
