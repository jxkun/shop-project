package com.lunarku.shop.service;

import java.util.List;

import com.lunarku.shop.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);
}
