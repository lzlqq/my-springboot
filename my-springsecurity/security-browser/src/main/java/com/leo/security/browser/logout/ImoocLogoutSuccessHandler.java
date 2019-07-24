package com.leo.security.browser.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.support.SimpleResponse;

public class ImoocLogoutSuccessHandler implements LogoutSuccessHandler{

	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	private SecurityProperties securityProperties;
	
	private  ObjectMapper  objectMapper = new ObjectMapper();
	public ImoocLogoutSuccessHandler(SecurityProperties securityProperties) {
		this.securityProperties=securityProperties;
	}
	

	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("退出成功");
		
		String signOutUrl=securityProperties.getBrowser().getSignOutUrl();
		if(StringUtils.isBlank(signOutUrl)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
		}else {
			response.sendRedirect(signOutUrl);
		}
	}

}
