package com.lunarku.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.content.service.ContentCategoryService;
import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbContentCategoryMapper;
import com.lunarku.shop.pojo.TbContentCategory;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCatgoryMapper;
	
	/**
	 * 通过父节点获取子节点
	 */
	@Override
	public List<EasyUITreeNode> getContentCatgory(Long parentId) {
		TbContentCategory contentCatgory = new TbContentCategory();
		contentCatgory.setParentId(parentId);
		List<TbContentCategory> list= contentCatgoryMapper.selectByRecord(contentCatgory);
		
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for(TbContentCategory category: list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent() ? "closed":"open");
			result.add(node);
		}
		
		return result;
	}

	/**
	 * 添加节点
	 */
	@Override
	public ResponseResult addContentCatgory(long parentId, String name) {
		Date date = new Date();
		// 添加catgory
		TbContentCategory contentCatgory = new TbContentCategory();
		contentCatgory.setParentId(parentId);
		contentCatgory.setName(name);
		contentCatgory.setCreated(date);
		contentCatgory.setUpdated(date);
		contentCatgory.setIsParent(false);
		contentCatgory.setStatus(1);
		contentCatgory.setSortOrder(1);
		contentCatgoryMapper.insertSelective(contentCatgory);
		
		// 更新父节点的isparent属性
		TbContentCategory parentCategory = contentCatgoryMapper.selectByPrimaryKey(parentId);
		parentCategory.setIsParent(true);
		parentCategory.setUpdated(date);
		contentCatgoryMapper.updateByPrimaryKey(parentCategory);
		
		return ResponseResult.ok(contentCatgory);
	}

	/**
	 * 更新节点
	 */
	@Override
	public ResponseResult updateContentCatgory(long id, String name) {
		Date date = new Date();
		TbContentCategory contentCategory = contentCatgoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(date);
		
		return ResponseResult.ok();
	}

	/**
	 * 删除节点
	 */
	@Override
	public ResponseResult deleteContentCatgory(long id) {
		TbContentCategory contentCategory = contentCatgoryMapper.selectByPrimaryKey(id);
		boolean isParent = contentCategory.getIsParent();
		// 如果该节点不为叶子节点，则递归删除其子节点                                              
		if(isParent) {
			TbContentCategory record = new TbContentCategory();
			record.setParentId(id);
			List<TbContentCategory> list = contentCatgoryMapper.selectByRecord(record);
			for(TbContentCategory child : list) {
				deleteContentCatgory(child.getId());
			}
		}
		contentCatgoryMapper.deleteByPrimaryKey(id);
		TbContentCategory parentCatgory = contentCatgoryMapper.selectByPrimaryKey(contentCategory.getParentId());
		parentCatgory.setIsParent(false);
		parentCatgory.setUpdated(new Date());
		
		contentCatgoryMapper.updateByPrimaryKeySelective(parentCatgory);
		return ResponseResult.ok();
	}
}
