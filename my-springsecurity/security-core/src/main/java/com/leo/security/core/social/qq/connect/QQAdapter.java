/**
 * 
 */
package com.leo.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.leo.security.core.social.qq.api.QQ;
import com.leo.security.core.social.qq.api.QQUserInfo;

/**
 * @author zhailiang
 *
 */
public class QQAdapter implements ApiAdapter<QQ> {

	@Override
	public boolean test(QQ api) {
		return true;
	}
	/**
	 * 将从服务提供商的个性化信息，设置到标准结构上
	 */
	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();
		
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_qq_1());//简单头像
		values.setProfileUrl(null);//比如主页url
		values.setProviderUserId(userInfo.getOpenId());//服务提供商的用户id，用户唯一标识
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		// 也是走api获取个人详细信息的接口，绑定解绑可以用到
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		//do noting 有些第三方应用有，比如微博可以发个消息更新个人主页
	}

}
