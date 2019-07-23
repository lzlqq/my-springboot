/**
 * 
 */
package com.leo.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.security.core.authentication.AbstractChannelSecurityConfig;
import com.leo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.leo.security.core.properties.SecurityConstants;
import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.validate.code.ValidateCodeSecurityConfig;

/**
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
//public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;
	
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
	
//	@Autowired
//	private ValidateCodeFilter validateCodeFilter;
	
//	@Autowired
//	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
//	
//	@Autowired
//	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//			.formLogin()
//			//http.httpBasic()
//				//.loginPage("/imooc-signIn.html")  //登录页面
//				.loginPage("/authentication/require")  //自定义认证控制器接口,SpringSecurity判断某个接口需要认证登录，这个接口负责给前端返回页面或者JSON
//				.loginProcessingUrl("/authentication/form") //上面那个配置如果返回时页面的化，页面上面的登录表单接口是这个，SpringSecurity中验证这个接口使用UsernamePasswordxx过滤器验证登录
//				.successHandler(imoocAuthenticationSuccessHandler) //自定义成功认证之后处理器
//				.failureHandler(imoocAuthenticationFailureHandler)//自定义失败认证之后处理器
//				.and()
//			.rememberMe()
//				.tokenRepository(persistentTokenRepository())
//				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//				.userDetailsService(userDetailsService)
//				.and()			
//			.authorizeRequests()
//			//.antMatchers("/authentication/require").permitAll()
//			.antMatchers("/authentication/require",
//					securityProperties.getBrowser().getLoginPage(),
//					"/code/*").permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.csrf().disable();
//	}
//	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		applyPasswordAuthenticationConfig(http);
		

		http.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.apply(imoocSocialSecurityConfig)
				.and()
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
			.authorizeRequests()
				.antMatchers(
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
					securityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
					securityProperties.getBrowser().getSignUpUrl(),
//					securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
//					securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
					securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
					securityProperties.getBrowser().getSignOutUrl(),
					"/user/regist"
//					,"/session/invalid"
					)
					.permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf().disable();
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
	
}
