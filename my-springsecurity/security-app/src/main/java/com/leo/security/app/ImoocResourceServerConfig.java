/**
 * 
 */
package com.leo.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.leo.security.core.properties.SecurityConstants;
import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.validate.code.ValidateCodeSecurityConfig;

/**
 * app资源服务器配置
 * 
 * @author zhailiang
 * 
 * 认证服务器同时作为资源服务器，别人来访问资源的时候配置应该是怎么样的
 *
 */
@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//	
//	@Autowired
//	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer imoocSocialSecurityConfig;
//	
//	@Autowired
//	private AuthorizeConfigManager authorizeConfigManager;
//	
//	@Autowired
//	private FormAuthenticationConfig formAuthenticationConfig;
	@Autowired
	private SecurityProperties securityProperties;
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		
//		formAuthenticationConfig.configure(http);
//		
//		http.apply(validateCodeSecurityConfig)
//				.and()
//			.apply(smsCodeAuthenticationSecurityConfig)
//				.and()
//			.apply(imoocSocialSecurityConfig)
//				.and()
//			.apply(openIdAuthenticationSecurityConfig)
//				.and()
//			.csrf().disable();
//		
//		authorizeConfigManager.config(http.authorizeRequests());
//	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
		.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
		.successHandler(imoocAuthenticationSuccessHandler)
		.failureHandler(imoocAuthenticationFailureHandler);
		

		http .apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.apply(imoocSocialSecurityConfig)
				.and()
			.authorizeRequests()
				.antMatchers(
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
					securityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
					securityProperties.getBrowser().getSignUpUrl(),
					securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
					securityProperties.getBrowser().getSignOutUrl(),
					"/user/regist"
					)
					.permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf().disable();
	}
	
}