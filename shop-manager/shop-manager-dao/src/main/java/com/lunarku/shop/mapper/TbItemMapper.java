package com.lunarku.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.lunarku.shop.common.mapper.BaseMapper;
import com.lunarku.shop.pojo.TbItem;

public interface TbItemMapper extends BaseMapper<TbItem>{
	
	int deleteByIds(@Param("ids") Long[] ids);
	
	int setStatus(@Param("ids") Long[] ids, @Param("status") byte status);
}