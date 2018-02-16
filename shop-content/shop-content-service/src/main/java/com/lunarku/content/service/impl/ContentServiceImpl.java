package com.lunarku.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lunarku.content.service.ContentService;
import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbContentMapper;
import com.lunarku.shop.pojo.TbContent;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public EasyUIDataResult selectContent(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		
		TbContent content = new TbContent();
		content.setCategoryId(categoryId);
		List<TbContent> list = contentMapper.selectByRecord(content);
		
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		
		EasyUIDataResult result = new EasyUIDataResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Override
	public ResponseResult addContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		return ResponseResult.ok();
	}

	@Override
	public ResponseResult updateContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.updateByPrimaryKey(content);
		return ResponseResult.ok();
	}

	@Override
	public TbContent selsectContent(long id) {
		TbContent content = contentMapper.selectByPrimaryKey(id);
		return content;
	}

	@Override
	public ResponseResult deleteContent(long id) {
		contentMapper.deleteByPrimaryKey(id);
		return ResponseResult.ok();
	}

	@Override
	public ResponseResult deleteContents(long[] ids) {
		contentMapper.deleteContents(ids);
		return ResponseResult.ok();
	}

	/**
	 * 通过分类id取content
	 */
	@Override
	public List<TbContent> getContentByCid(long cid) {
		TbContent content = new TbContent();
		content.setCategoryId(cid);
		
		List<TbContent> result = contentMapper.selectByRecord(content);
		return result;
	}
}
