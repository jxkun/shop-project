package com.lunarku.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.mapper.TbItemMapper;
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
	@Override
	public EasyUIDataResult getItemList(int page, int rows) {
		// 设置分页信息，参数：查询的页数，每页显示的记录数
		PageHelper.startPage(page, rows);
		// 查询
		List<TbItem> list =  itemMapper.selectByRecord(null);
		// 获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		// 创建查询返回结果对象
		EasyUIDataResult result = new EasyUIDataResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}
}
