package com.lunarku.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.lunarku.shop.common.mapper.BaseMapper;
import com.lunarku.shop.pojo.TbContent;

public interface TbContentMapper extends BaseMapper<TbContent>{

	int deleteContents(@Param("ids") long[] ids);
}