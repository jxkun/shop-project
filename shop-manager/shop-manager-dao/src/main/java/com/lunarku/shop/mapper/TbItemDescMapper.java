package com.lunarku.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.lunarku.shop.common.mapper.BaseMapper;
import com.lunarku.shop.pojo.TbItemDesc;

public interface TbItemDescMapper extends BaseMapper<TbItemDesc>{
	int deleteByIds(@Param("ids") Long[] ids);
}