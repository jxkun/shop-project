package com.lunarku.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.search.mapper.SearchItemMapper;
import com.lunarku.search.service.SearchItemService;
import com.lunarku.shop.common.pojo.SearchItem;
import com.lunarku.shop.common.util.ResponseResult;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 向solr中导入商品索引
	 */
	@Override
	public ResponseResult importItemIndex() {
		try {
			//1、先查询所有商品数据
			List<SearchItem> itemList = searchItemMapper.getItemList();
			//2、遍历商品数据添加到索引库
			for(SearchItem item : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				//向文档中添加域
				document.addField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getItem_desc());
				//把文档写入索引库
				solrServer.add(document);
			}
			//3、提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, "导入索引失败", null);
		}
		
		//4、返回添加成功
		return ResponseResult.ok();
	}
	
	public ResponseResult updateItemIndex() {
		try {
			//1. 先删除之前全部的索引
			solrServer.deleteByQuery("*:*");
			List<SearchItem> itemList = searchItemMapper.getItemList();
			//2、遍历商品数据添加到索引库
			for(SearchItem item : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				//向文档中添加域
				document.addField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getItem_desc());
				//把文档写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, "更新失败", null);
		}
		return ResponseResult.ok();
	}
}
