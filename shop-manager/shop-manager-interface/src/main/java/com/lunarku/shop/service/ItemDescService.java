package com.lunarku.shop.service;

import com.lunarku.shop.pojo.TbItemDesc;

public interface ItemDescService {
	
	TbItemDesc getItemDescById(long id);
	
	boolean updateItemDesc(TbItemDesc itemDescs);
	
}
