package com.knowology.handler;

import com.alibaba.fastjson.JSON;
import com.knowology.domain.OccsUserDetails;
import com.knowology.util.JwtUtil;
import com.knowology.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录成功处理类
 * @author: Conan
 * @create: 2019-01-20 15:52
 **/
public class SysAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		httpServletResponse.setContentType("application/json;charset=utf-8");
		PrintWriter pw = httpServletResponse.getWriter();
		String username = ((OccsUserDetails) authentication.getPrincipal()).getUsername();
		String fullName = ((OccsUserDetails) authentication.getPrincipal()).getFullName();
		Integer userId = ((OccsUserDetails) authentication.getPrincipal()).getUserId();
		Map<String, Object> obj = new HashMap<>();
		final String token = JwtUtil.generateToken(username, fullName, userId);
		//添加token桶，后续在JwtAuthenticationFilter中校验token的有效性
		JwtUtil.addTokenToBucket(username,token);
		obj.put("token",token);
		obj.put("fullName",fullName);
		obj.put("userId",userId);
		String s = JSON.toJSONString(ResponseUtil.ok(obj));
		pw.write(s);
		pw.flush();
		pw.close();
	}
}
