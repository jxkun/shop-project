package com.lunarku.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lunarku.shop.common.util.CookieUtils;
import com.lunarku.shop.common.util.ResponseResult;
import com.lunarku.shop.pojo.TbUser;
import com.lunarku.sso.service.UserService;

/**
 * 判断用户是否登入
 * <p>Title: LoginIterceptor</p>
 * @author 
 * @version 1.0
 */
public class LoginIterceptor implements HandlerInterceptor{

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler之前先执行此方法
		// 从cookie中获取token，判断是否为空
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		if(StringUtils.isBlank(token)) {
			String requestURL = request.getRequestURL().toString();
			response.sendRedirect(SSO_URL + "/user/login?url=" + requestURL);
			// 拦截
			return false;
		}
		
		// 通过token查询用户是否登入
		ResponseResult result = userService.getUserByToken(token);
		if(result.getStatus() != 200) {
			String requestURL = request.getRequestURL().toString();
			response.sendRedirect(SSO_URL + "/user/login?url=" + requestURL);
			// 拦截
			return false;
		}
		TbUser user = (TbUser)result.getData();
		request.setAttribute("user", user);
		// 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler执行之后，modelAndView返回之前
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 在ModelAndView返回之后，异常处理

	}

}
