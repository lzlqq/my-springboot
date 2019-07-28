/**
 * 
 */
package com.leo.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.security.core.properties.SecurityProperties;

/**
 * 社交登录配置主类
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;// 本Demo中是DemoConnectionSignUp

	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	/**
	 * create table imooc_UserConnection ( userId varchar(255) not
	 * null,#业务系统用户id，可以是通过providerId和openId（providerUserId，从服务商获取的服务商用户id）默认生产，参见注册部分，之后通过userId，调用UserDetailService获取详细信息
	 * providerId varchar(255) not null,#服务提供商id providerUserId
	 * varchar(255),#服务提供商上的用户id，也就是openId rank int not null, displayName
	 * varchar(255), profileUrl varchar(512), imageUrl varchar(512), accessToken
	 * varchar(512) not null, secret varchar(512), refreshToken varchar(512),
	 * expireTime bigint, primary key (userId, providerId, providerUserId)); create
	 * unique index UserConnectionRank on UserConnection(userId, providerId, rank);
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		// 第三个参数是对插入到数据库中的数据做加密，这里为了看的清楚，没有做任何处理，即noOpText
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		// 为表名增加前缀
		repository.setTablePrefix("imooc_");
		if (connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	/**
	 * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
	 * @return
	 */
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
		// SocialAuthenticationFilter过滤器默认拦截的请求是/auth开头，这里获取自己配置的处理路径/login
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
		return configurer;
	}

	/**
	 *  用来处理注册流程的工具类
	 * @param connectionFactoryLocator
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
}
