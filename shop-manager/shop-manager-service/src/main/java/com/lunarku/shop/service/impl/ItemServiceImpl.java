package com.lunarku.shop.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lunarku.shop.common.pojo.EasyUIDataResult;
import com.lunarku.shop.common.util.IDUtil;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbItemDescMapper;
import com.lunarku.shop.mapper.TbItemMapper;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.redis.JedisClient;
import com.lunarku.shop.service.ItemService;

/**
 *  商品管理服务
 */
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource(name="itemTopic")
	private Destination destination;
	
	@Autowired
	private JedisClient jedisClient;
	/** item缓存前缀*/
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	/** 过期时间，默认一天*/
	@Value("${TIEM_EXPIRE}")
	private Integer TIEM_EXPIRE;
	
	
	@Override
	public TbItem getItemById(long itemId) {
		String key = ITEM_INFO + ":" + itemId + "ITEM";
		// 查询前先查缓存,缓存不能影响正常逻辑
		try {
			String json = jedisClient.get(key);
			if(StringUtils.isNotBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem ibItem = itemMapper.selectByPrimaryKey(itemId);
		// 查询后,设置缓存
		try {
			jedisClient.set(key, JsonUtils.objectToJson(ibItem));
			jedisClient.expire(key, TIEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public ResponseResult addItem(TbItem item, String desc) {
		long itemId = IDUtil.getItemId();
		item.setId(itemId);
		// 商品状态  1-正常； 2-下架； 3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//添加item
		itemMapper.insert(item);
		
		// 添加 itemDesc
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
		
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(itemId + "");
				return textMessage;
			}
		});
		// 返回结果
		return ResponseResult.ok();
	}

	/**
	 * 批量删除
	 */
	@Override
	public boolean deleteByIds(Long[] ids) {
		itemMapper.deleteByIds(ids);
		itemDescMapper.deleteByIds(ids);
		return true;
	}

	/**
	 * 批量上下架
	 */
	@Override
	public boolean setStatus(Long[] ids, byte status) {
		itemMapper.setStatus(ids, status);
		return true;
	}

	@Override
	public boolean updateItem(TbItem item) {
		itemMapper.updateByPrimaryKey(item);
		return true;
	}	
}
