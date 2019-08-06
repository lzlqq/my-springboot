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
public class QQAdapter implements ApiAdapter<QQ>{

    @Override
    public boolean test(QQ api){
        // 测试当前API是否空，也就是QQ服务是够可用，返回true，不去测试是否可用，表示永远可用
        return true;
    }

    /**
     * 将从服务提供商的个性化信息，设置到标准结构上
     * 将个性化的数据转换为标准的数据结构
     */
    @Override
    public void setConnectionValues(QQ api,ConnectionValues values){
        // 获取用户信息
        QQUserInfo userInfo = api.getUserInfo();
        // 显示的用户名字
        values.setDisplayName(userInfo.getNickname());
        // 用户头像,简单头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页，qq没有,比如主页url
        values.setProfileUrl(null);
        // 服务提供商的用户id，即openId，用户唯一标识
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateStatus(QQ api,String message){
        //do noting
    }

}
