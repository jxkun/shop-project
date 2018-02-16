package com.lunarku.search.service;

import com.lunarku.shop.common.pojo.SearchResult;

public interface SearchService {

	public SearchResult search(String queryString, int page, int rows) throws Exception;
}
