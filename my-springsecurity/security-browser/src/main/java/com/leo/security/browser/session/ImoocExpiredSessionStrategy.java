package com.leo.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

public class ImoocExpiredSessionStrategy implements SessionInformationExpiredStrategy{

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
		eventØ.getResponse().setContentType("application/json;charset=UTF-8");
		eventØ.getResponse().getWriter().write("並發登錄！");
	}

}
