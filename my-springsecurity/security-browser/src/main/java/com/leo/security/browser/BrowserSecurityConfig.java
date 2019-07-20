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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.validate.code.ValidateCodeFilter;

/**
 * @author zhailiang
 *
 */
@Configuration
//public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
//	
//	@Autowired
//	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//	
//	@Autowired
//	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
//	
//	@Autowired
//	private SpringSocialConfigurer imoocSocialSecurityConfig;
//	
//	@Autowired
//	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
//	
//	@Autowired
//	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private ValidateCodeFilter validateCodeFilter;
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.formLogin()
			//http.httpBasic()
				//.loginPage("/imooc-signIn.html")  //登录页面
				.loginPage("/authentication/require")  //自定义认证控制器接口
				.loginProcessingUrl("/authentication/form") //申明使用UsernamePasswordxx过滤器验证登录
				.successHandler(imoocAuthenticationSuccessHandler) //自定义成功认证之后处理器
				.failureHandler(imoocAuthenticationFailureHandler)//自定义失败认证之后处理器
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()			
			.authorizeRequests()
			//.antMatchers("/authentication/require").permitAll()
			.antMatchers("/authentication/require",
					securityProperties.getBrowser().getLoginPage(),
					"/code/*").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable();
	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		applyPasswordAuthenticationConfig(http);
//		
//		http.apply(validateCodeSecurityConfig)
//				.and()
//			.apply(smsCodeAuthenticationSecurityConfig)
//				.and()
//			.apply(imoocSocialSecurityConfig)
//				.and()
//			.rememberMe()
//				.tokenRepository(persistentTokenRepository())
//				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//				.userDetailsService(userDetailsService)
//				.and()
//			.sessionManagement()
//				.invalidSessionStrategy(invalidSessionStrategy)
//				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
//				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
//				.expiredSessionStrategy(sessionInformationExpiredStrategy)
//				.and()
//				.and()
//			.authorizeRequests()
//				.antMatchers(
//					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
//					securityProperties.getBrowser().getLoginPage(),
//					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
//					securityProperties.getBrowser().getSignUpUrl(),
//					securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
//					securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
//					"/user/regist")
//					.permitAll()
//				.anyRequest()
//				.authenticated()
//				.and()
//			.csrf().disable();
//		
//	}
//
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
