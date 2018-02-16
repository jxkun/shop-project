package com.lunarku.shop.service;

import java.util.List;

import com.lunarku.shop.pojo.TbItemDesc;

public interface ItemDescService {
	
	TbItemDesc getItemDescById(long id);
	
	boolean updateItemDesc(TbItemDesc itemDescs);
}
