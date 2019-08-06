/**
 * 
 */
package com.leo.security.core.properties;

/**
 * 社交登录配置项
 *
 */
public class SocialProperties{

    /**
     * SocialAuthenticationFilter 中默认的认证前缀DEFAULT_FILTER_PROCESSES_URL = "/auth"，
     * 认证qq则需要/auth/qq,url上的qq是qq的providerId，默认就叫qq，同理微信也类似;
     * 在demo中配置注册时候用的url ：/qqLogin，注册应用时的providerId是：callback.do，
     * 以上url以供第三方应用和qq互相调用
     */
    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    /**
     * 创建一个qq配置对象
     */
    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    public QQProperties getQq(){
        return qq;
    }

    public void setQq(QQProperties qq){
        this.qq = qq;
    }

    public String getFilterProcessesUrl(){
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeixinProperties getWeixin(){
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin){
        this.weixin = weixin;
    }

}
