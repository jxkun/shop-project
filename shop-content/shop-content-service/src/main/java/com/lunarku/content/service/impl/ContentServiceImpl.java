package com.lunarku.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lunarku.content.redis.JedisClient;
import com.lunarku.content.service.ContentService;
import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbContentMapper;
import com.lunarku.shop.pojo.TbContent;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	
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
		
		// 同步缓存
		// 删除对应缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
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
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			// 返回结果
			if(StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 若缓存没有，则查询数据库
		TbContent content = new TbContent();
		content.setCategoryId(cid);
		
		List<TbContent> result = contentMapper.selectByRecord(content);
		
		// 把结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
