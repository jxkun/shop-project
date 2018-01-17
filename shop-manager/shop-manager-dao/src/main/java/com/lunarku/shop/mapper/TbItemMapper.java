package com.lunarku.shop.mapper;

import java.util.List;

import com.lunarku.shop.pojo.TbItem;

public interface TbItemMapper {
	
	List<TbItem> selectByItem(TbItem item);
	
    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    TbItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);
}