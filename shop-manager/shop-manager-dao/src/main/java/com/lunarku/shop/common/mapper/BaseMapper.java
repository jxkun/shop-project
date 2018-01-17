package com.lunarku.shop.common.mapper;

import java.util.List;

public interface BaseMapper<T> {
    List<T> selectByRecord(T record);
	
	int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
