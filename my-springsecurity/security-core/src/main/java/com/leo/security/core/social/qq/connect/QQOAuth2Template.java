/**
 * 
 */
package com.leo.security.core.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * QQ社交登录模板
 *
 */
public class QQOAuth2Template extends OAuth2Template{

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 默认是false，不带client_id和client_secret，需要设置为true
        // 发请求的时候携带参数
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 处理QQ特殊的返回成功信息,不是json的
     * 
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl,MultiValueMap<String, String> parameters){
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        logger.info("获取accessToke的响应：" + responseStr);
        /**
         * 截取认证成功后的信息
         */
        // QQ互联上是如果成功返回，即可在返回包中获取到Access Token。 如：
        // access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        /**
         *
         * accessToken
         */
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        /**
         * expiresIn
         */
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        /**
         * 刷新令牌
         */
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate(){
        RestTemplate restTemplate = super.createRestTemplate();
        /**
         * 在原有的实现之上添加了一个能够处理text/html信息的converter
         */
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
