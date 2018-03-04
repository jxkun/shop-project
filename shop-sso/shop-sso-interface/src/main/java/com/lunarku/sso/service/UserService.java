package com.lunarku.sso.service;

import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbUser;

public interface UserService {
	
	ResponseResult checkData(String data, int type);
	ResponseResult register(TbUser user);
	ResponseResult login(String userName, String password);
	ResponseResult getUserByToken(String token);
	ResponseResult loginOut(String token);
}
