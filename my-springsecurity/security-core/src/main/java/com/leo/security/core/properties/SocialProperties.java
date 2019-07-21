/**
 * 
 */
package com.leo.security.core.properties;

/**
 * @author zhailiang
 *
 */
public class SocialProperties {
	/**
	 * SocialAuthenticationFilter 中默认的认证前缀DEFAULT_FILTER_PROCESSES_URL = "/auth"，认证qq
	 * 则需要/auth/qq,url上的qq是qq的providerId，默认就叫qq，同理微信也类似;
	 */
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin = new WeixinProperties();

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public WeixinProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinProperties weixin) {
		this.weixin = weixin;
	}
	
}
