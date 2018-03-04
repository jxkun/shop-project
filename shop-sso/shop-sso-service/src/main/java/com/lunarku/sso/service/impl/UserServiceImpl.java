package com.lunarku.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.lunarku.shop.common.util.CookieUtils;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.mapper.TbUserMapper;
import com.lunarku.shop.pojo.TbUser;
import com.lunarku.sso.redis.JedisClient;
import com.lunarku.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient ;
	
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	/**
	 * 校验数据合法性
	 * @param data
	 * @param type
	 * @return
	 */
	@Override
	public ResponseResult checkData(String data, int type) {
		TbUser user = new TbUser();
		//设置查询条件
		//1.判断用户名是否可用
		if (type == 1) {
			user.setUsername(data);
		//2判断手机号是否可以使用
		} else if (type == 2) {
			user.setPhone(data);
		//3判断邮箱是否可以使用
		} else if (type == 3) {
			user.setEmail(data);
		} else {
			return ResponseResult.build(400, "参数包含非法字符", null);
		}
		// 执行查询
		List<TbUser> list = userMapper.selectByRecord(user);
		if(list != null && list.size() > 0) {
			return ResponseResult.ok(false);
		}
		// 数据可以使用
		return ResponseResult.ok(true);
	}

	/**
	 * 注册逻辑实现
	 * @param user
	 * @return
	 */
	@Override
	public ResponseResult register(TbUser user) {
		// 检查数据有效性
		if(StringUtils.isBlank(user.getUsername())) {
			return ResponseResult.build(400, "用户名不能为空", null);
		}
		// 判断用户名是否重复
		ResponseResult checkUserName = checkData(user.getUsername(), 1);
		if(!(boolean)checkUserName.getData()) {
			return ResponseResult.build(400, "用户名重复", null);
		}
		// 判断密码是否为空
		if(StringUtils.isBlank(user.getPassword())) {
			return ResponseResult.build(400, "密码不能为空");
		}
		// 判断电话,邮箱是否重复
		if(StringUtils.isNotBlank(user.getPhone())) {
			ResponseResult checkPhone = checkData(user.getPhone(), 2);
			if(!(boolean)checkPhone.getData()) {
				return ResponseResult.build(400, "此电话号码已经被绑定，请换一个重试");
			}
		}
		if(StringUtils.isNotBlank(user.getEmail())) {
			ResponseResult checkEmail = checkData(user.getEmail(), 3);
			if(!(boolean)checkEmail.getData()) {
				return ResponseResult.build(400, "此邮箱账号已经被绑定，请换一个重试");
			}
		}
		// 补全pojo属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 密码md5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		// 插入数据
		userMapper.insert(user);
		
		return ResponseResult.ok();
	}

	/**
	 * 登入方法
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public ResponseResult login(String userName, String password) {
		// 判断用户名和密码是否正确
		TbUser user = new TbUser();
		user.setUsername(userName);
		List<TbUser> list = userMapper.selectByRecord(user);
		if(list == null || list.size() == 0) {
			return ResponseResult.build(400, "用户名或密码不正确");
		}
		
		TbUser resultUser = list.get(0);
		// 校验密码是否正确
		if(!DigestUtils.md5DigestAsHex(password.getBytes())
				.equals(resultUser.getPassword())) {
			return ResponseResult.build(400, "用户名或密码不正确");
		}
		
		// 生成token
		String token = UUID.randomUUID().toString();
		// 清空密码
		resultUser.setPassword(null);
		// 把用户信息保存到redis，key为token，value为用户信息。
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(resultUser));
		// 设置token过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		// 返回登入成功, 返回token
		return ResponseResult.ok(token);
	}

	/**
	 * 获取token验证
	 * @param token
	 * @return
	 */
	@Override
	public ResponseResult getUserByToken(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if(StringUtils.isBlank(json)) {
			return ResponseResult.build(400, "用户登入已过期，请重新登入");
		}
		// 重置Session过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		// 把json转成user对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
	
		return ResponseResult.ok(user);
	}

	@Override
	public ResponseResult loginOut(String token) {
		jedisClient.del(USER_SESSION + ":" + token);
		return ResponseResult.ok();
	}
}
