package com.lunarku.shop.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.mapper.TbItemDescMapper;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.redis.JedisClient;
import com.lunarku.shop.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Autowired
	private JedisClient jedisClient;
	/** item缓存前缀*/
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	/** 过期时间，默认一天*/
	@Value("${TIEM_EXPIRE}")
	private Integer TIEM_EXPIRE;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	public TbItemDesc getItemDescById(long id) {
		String key = ITEM_INFO + ":" + id + ":" + "DESC";
		// 先查询缓存
		try {
			String json = jedisClient.get(key);
			if(StringUtils.isNotBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		// 在redis中设置缓存
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(key, TIEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

	@Override
	public boolean updateItemDesc(TbItemDesc itemDescs) {
		int count = itemDescMapper.updateByPrimaryKey(itemDescs);
		return true;
	}

}
