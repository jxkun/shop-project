package com.lunarku.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lunarku.search.dao.SearchDao;
import com.lunarku.search.service.SearchService;
import com.lunarku.shop.common.pojo.SearchResult;

public class SearchServiceTest {
	
	/**
	 * 测试SearchDao
	 * @throws Exception
	 */
	@Test
	public void testSearchDao() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		SearchDao searchDao = context.getBean(SearchDao.class);
		
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件, 过滤条件, 分页条件, 排序条件, 高亮
		//query.set("q","*:*");
		query.setQuery("手机");
		//分页条件
		query.setStart(0);
		query.setRows(10);
		//设置默认搜索域
		query.set("df", "item_keywords");
		//设置高亮
		query.setHighlight(true);
		// 设置高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		System.out.println(query);
		//执行查询，得到一个Response对象
		SearchResult result = searchDao.search(query);
		System.out.println(result.getRecordCount());
	}
	
	@Test
	public void testSearchService() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		SearchService searchService = context.getBean(SearchService.class);
		SearchResult searchResult = searchService.search("手机", 1, 10);
		System.out.println(searchResult.getRecordCount());
	}
	
	
}
