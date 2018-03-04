package com.lunarku.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lunarku.shop.common.util.CookieUtils;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbUser;
import com.lunarku.sso.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public ResponseResult checkData(@PathVariable String param, 
			@PathVariable Integer type) {
		ResponseResult result = userService.checkData(param, type);
		return result;
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult register(TbUser user) {
		ResponseResult result = userService.register(user);
		return result;
	}
	
	/**
	 * 登入
	 * @param username
	 * @param password
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult login(String username, String password,
			HttpServletResponse response, HttpServletRequest request) {
		// 检验用户是否已经登入，若登入则返回400错误
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		ResponseResult userByToken = userService.getUserByToken(token);
		TbUser data = (TbUser)userByToken.getData();
		if(data != null && data.getUsername().equals(username)) {
			return ResponseResult.build(400, "用户已登入，请勿重复登入");
		}
		// 不是重复登入，则执行login方法
		ResponseResult result = userService.login(username, password);
		// 登入成功后写入cookie
		if(result.getStatus() == 200) {
			// 把token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
	/**
	 * 通过token查询user,jquery通过callback函数解决js跨域访问问题
	 * 方法一
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET,
			// 指定返回响应数据的context-type
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback) {
		ResponseResult result = userService.getUserByToken(token);
		// 判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}
	
/**  // 解决js跨域,方法二： 需要spring4.1 以上支持
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken2(@PathVariable String token, String callback) {
		ResponseResult result = userService.getUserByToken(token);
		// 判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			// 设置回调方法
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
*/	
	@RequestMapping(value="/user/logout/{token}")
	@ResponseBody
	public ResponseResult loginOut(@PathVariable String token,
			HttpServletRequest request,
			HttpServletResponse response) {
		ResponseResult result = userService.loginOut(token);
		CookieUtils.deleteCookie(request, response, TOKEN_KEY);
		return result;
	}
}
