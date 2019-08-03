/**
 * 
 */
package com.leo.security.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.leo.security.core.properties.OAuth2ClientProperties;
import com.leo.security.core.properties.SecurityProperties;

/**
 * 认证服务器配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
// 此处要是集成了AuthorizationServerConfigurerAdapter，会报java.lang.UnsupportedOperationException: Cannot build client services (maybe use inMemory() or jdbc()).
// copy代码，一定要核对清楚，既然原来都没问题，新加copy了代码进来，而视频中没问题的，说明copy进来的时候，肯定是多了什么，或者少了什么

//
//	/**
//	 * 继承了AuthorizationServerConfigurerAdapter类，就需要注入userDetailsService和authenticationManager
//	 * 如果没有继承，使用默认的机制不需要注入，Spring自己回去查找
//	 */
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired(required = false)
	private TokenEnhancer jwtTokenEnhancer;

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * 认证及token配置
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore) // 将token存储到redis中
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);

		// 如果配置了imooc.security.oauth2.tokenStore=jwt或者根本没有配置imooc.security.oauth2.tokenStore，那么使用的是JWT
		if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
			// TokenEnhancerChain是增强器链
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(jwtTokenEnhancer);
			enhancers.add(jwtAccessTokenConverter);
			enhancerChain.setTokenEnhancers(enhancers);
			endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
		}

	}
//
//	/**
//	 * tokenKey的访问权限表达式配置
//	 */
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.tokenKeyAccess("permitAll()");
//	}

	/**
	 * 客户端配置
	 * 我们这里不会像qq或者微信那样允许其他应用注册，所以将客户端信息放在内存中就可以了
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 客户端信息在配置文件中配置
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
			for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("refresh_token", "authorization_code", "password") // 客户端支持的授权模式
						.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds()) // 令牌的有效时间
						.refreshTokenValiditySeconds(2592000)
						.scopes("all"); // 令牌的权限有哪些，可以配置多个，这里配置了，客户端请求时就不用带scope参数了.如果客户端带了scope参数，则必须是配置的scope中的其中一个，否则会报错
			}
		}
	}
	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory().withClient("imooc")
//		.secret("imoocsecret")
//		.authorizedGrantTypes("refresh_token", "authorization_code", "password") // 客户端支持的授权模式
//		.accessTokenValiditySeconds(7200) // 令牌的有效时间
//		.refreshTokenValiditySeconds(2592000)
//		.scopes("all","read","write");
//	}

}
