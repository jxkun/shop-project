package com.lunarku.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarku.search.dao.SearchDao;
import com.lunarku.search.service.SearchService;
import com.lunarku.shop.common.pojo.SearchResult;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception{
		if(page < 1) page = 1;
		int start = (page - 1)*rows;
		//创建SolrQuery查询对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(queryString);
		// 设置分页条件
		solrQuery.setStart(start);
		solrQuery.setRows(rows);
		//设置默认搜索域
		solrQuery.set("df", "item_title");
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		// 执行查询
		SearchResult searchResult = searchDao.search(solrQuery);
		//计算总页数
		long recordCount = searchResult.getRecordCount();
		Long pages = recordCount / rows;
		if(recordCount % rows > 0) {
			pages ++;
		}
		searchResult.setTotalPages(pages);
		return searchResult;
	}


}
