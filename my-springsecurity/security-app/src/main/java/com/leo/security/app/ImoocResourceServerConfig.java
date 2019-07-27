/**
 * 
 */
package com.leo.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 资源服务器配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig {
//extends ResourceServerConfigurerAdapter {
//	
//	@Autowired
//	protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
//	
//	@Autowired
//	protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;
//	
//	@Autowired
//	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//	
//	@Autowired
//	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
//	
//	@Autowired
//	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
//	
//	@Autowired
//	private SpringSocialConfigurer imoocSocialSecurityConfig;
//	
//	@Autowired
//	private AuthorizeConfigManager authorizeConfigManager;
//	
//	@Autowired
//	private FormAuthenticationConfig formAuthenticationConfig;
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
	
}