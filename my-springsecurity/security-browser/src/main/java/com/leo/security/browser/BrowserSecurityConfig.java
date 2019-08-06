/**
 * 
 */
package com.leo.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.security.core.authentication.FormAuthenticationConfig;
import com.leo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.leo.security.core.authorize.AuthorizeConfigManager;
import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.validate.code.ValidateCodeSecurityConfig;

/**
 * 浏览器环境下安全配置主类
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    // 数据源在application.properties中已经配置了
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 表单登录配置
        formAuthenticationConfig.configure(http);

        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(imoocSocialSecurityConfig)
                .and()
            //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
//			.sessionManagement()
//				.invalidSessionUrl("/session/invalid")
//				.maximumSessions(1)
//				.maxSessionsPreventsLogin(true)//控制已存在用户超过maximumSessions数时策略，能否再登陆，默认是false，已登录的被挤下线，设置为true时，再登陆报错
//				.expiredSessionStrategy(new ImoocExpiredSessionStrategy())
//				.and()
//				.and()
            .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)// session 过期策略
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy) // session并发策略
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
                //.logoutSuccessUrl("/imooc-logout.html")
                .logoutSuccessHandler(logoutSuccessHandler)//和url互斥
                .deleteCookies("JSESSIONID")//退出时删除cookie
                .and()
//			.authorizeRequests()
//				.antMatchers("/user").hasRole("ADMIN") //每一个权限表达式都是和一个antMatchers配合使用的
//				.antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")  //hasRole要求在UserDetailService中的权限前面加上ROLE_
//				.antMatchers(HttpMethod.GET,"/user/*").access("hasRole('ADMIN') and hasIpAddress('xxx')") // 混合使用
//				.anyRequest()
//				.authenticated()
//				.and()
            .csrf().disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }
    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 在JdbcTokenRepositoryImpl中有一个常亮CREATE_TABLE_SQL，就是用来创建表的sql，可以在数据库中执行这段sql
        // 也可以使用下面的方式创建，但是如果表已经存在了，下面的代码会报错
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
