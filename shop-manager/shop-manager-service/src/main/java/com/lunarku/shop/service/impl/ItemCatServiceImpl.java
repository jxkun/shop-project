package com.lunarku.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.mapper.TbItemCatMapper;
import com.lunarku.shop.pojo.TbItemCat;
import com.lunarku.shop.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCat itemCat = new TbItemCat();
		itemCat.setParentId(parentId);
		List<TbItemCat> lists = itemCatMapper.selectByRecord(itemCat);
		List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<EasyUITreeNode>();
		for(TbItemCat cat : lists) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(cat.getId());
			treeNode.setState(cat.getIsParent() ? "close":"open");
			treeNode.setText(cat.getName());
			easyUITreeNodeList.add(treeNode);
		}
		return easyUITreeNodeList;
	}

}
