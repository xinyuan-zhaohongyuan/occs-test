package com.knowology.handler;

import com.alibaba.fastjson.JSON;
import com.knowology.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 成功登出处理类
 * @author: Conan
 * @create: 2019-01-21 11:29
 **/

public class SysLogoutHandler implements LogoutHandler {
	private static final Logger logger = LoggerFactory.getLogger(SysLogoutHandler.class);

	public SysLogoutHandler() {

	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
		String sessionId = request.getRequestedSessionId();
		if (sessionId != null) {
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(JSON.toJSONString(ResponseUtil.ok()));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			logger.error("登出异常:"+e.getMessage());
		}
	}
}
