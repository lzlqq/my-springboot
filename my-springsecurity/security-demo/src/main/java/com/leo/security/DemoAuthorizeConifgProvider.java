/**
 * 
 */
package com.leo.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.leo.security.core.authorize.AuthorizeConfigProvider;

/**
 *
 */
@Component
public class DemoAuthorizeConifgProvider implements AuthorizeConfigProvider {

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers("/user/regist").permitAll();
		//demo项目授权配置
		return false; // 返回false，表示当前配置也没有anyRequest配置
	}

}
