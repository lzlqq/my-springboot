/**
 * 
 */
package com.leo.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhailiang
 *
 */
@Configuration
//public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private SecurityProperties securityProperties;
//	
//	@Autowired
//	private DataSource dataSource;
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		//http.httpBasic()
			.loginPage("/imooc-signIn.html")  //登录页面
			.loginProcessingUrl("/authentication/form") //申明使用UsernamePasswordxx过滤器验证登录
			.and()
			.authorizeRequests()
			.antMatchers("/imooc-signIn.html").permitAll()
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
//	
//	@Bean
//	public PersistentTokenRepository persistentTokenRepository() {
//		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//		tokenRepository.setDataSource(dataSource);
////		tokenRepository.setCreateTableOnStartup(true);
//		return tokenRepository;
//	}
	
}
