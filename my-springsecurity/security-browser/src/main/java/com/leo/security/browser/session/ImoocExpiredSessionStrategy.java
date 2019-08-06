package com.leo.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.leo.security.core.properties.SecurityProperties;

/**
 * 并发登录导致session失效时，默认的处理策略
 * 
 * @author LSH7120
 *
 */
public class ImoocExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy{

    public ImoocExpiredSessionStrategy(SecurityProperties securityPropertie){
        super(securityPropertie);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException,ServletException{
        //eventØ.getResponse().setContentType("application/json;charset=UTF-8");
        //eventØ.getResponse().getWriter().write("並發登錄！");

        onSessionInvalid(eventØ.getRequest(), eventØ.getResponse());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.imooc.security.browser.session.AbstractSessionStrategy#isConcurrency()
     */
    @Override
    protected boolean isConcurrency(){
        return true;
    }
}
