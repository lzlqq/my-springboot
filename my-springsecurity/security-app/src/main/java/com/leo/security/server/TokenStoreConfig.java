/**
 * 
 */
package com.leo.security.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.leo.security.core.properties.SecurityProperties;

/**
 *
 */
@Configuration
public class TokenStoreConfig {
	
	/**
	 * 使用redis存储token的配置，只有在imooc.security.oauth2.tokenStore配置为redis时生效
	 *
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "tokenStore", havingValue = "redis")
	public static class RedisConfig {
		
		@Autowired
		private RedisConnectionFactory redisConnectionFactory;
		
		/**
		 * @return
		 */
		@Bean
		public TokenStore redisTokenStore() {
			return new RedisTokenStore(redisConnectionFactory);
		}
		
	}

	/**
	 * 使用jwt时的配置，默认生效（matchIfMissing = true），为true时，不配置也生效
	 * 
	 *
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
	public static class JwtConfig {
		
		@Autowired
		private SecurityProperties securityProperties;
		
		/**
		 * @return
		 * 配置token的存储
		 */
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}
		
		/**
		 * @return
		 * 配置token的生成
		 * 
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter(){
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			// 指定签名的秘钥
	        converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
	        return converter;
		}
		
		/**
		 * @ConditionalOnBean
		 * @return
		 */
		@Bean
		@ConditionalOnBean(TokenEnhancer.class)
		// @ConditionalOnMissingBean(name="jwtTokenEnhancer") 配置这个注解，业务系统可以配置自己的jwtTokenEnhancer，来覆盖掉这个默认的逻辑
		public TokenEnhancer jwtTokenEnhancer(){
			return new TokenJwtEnhancer();
		}
		
	}
	
	

}
