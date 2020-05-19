package com.knowology.handler;

import com.alibaba.fastjson.JSON;
import com.knowology.exception.TokenInvalidException;
import com.knowology.util.ResponseUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 登录失败处理类
 * @author: Conan
 * @create: 2019-01-20 15:11
 **/
public class SysAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
		String message;
		if (exception instanceof UsernameNotFoundException) {
			message = "用户不存在！";
		} else if (exception instanceof BadCredentialsException) {
			message = "用户名或密码错误！";
		} else if (exception instanceof LockedException) {
			message = "用户已被锁定！";
		} else if (exception instanceof DisabledException) {
			message = "用户不可用！";
		} else if (exception instanceof AccountExpiredException) {
			message = "账户已过期！";
		} else if (exception instanceof CredentialsExpiredException) {
			message = "用户密码已过期！";
		} else if(exception instanceof TokenInvalidException) {
			message = "登陆信息已过期,请重新登陆";
		}else {
			message = "认证失败，请联系网站管理员！";
		}
 		httpServletResponse.setContentType(ResponseUtil.JSON_UTF8);
		PrintWriter pw = httpServletResponse.getWriter();
		String s = JSON.toJSONString(ResponseUtil.failAuthorization(message));
		pw.write(s);
		pw.flush();
		pw.close();
	}
}
