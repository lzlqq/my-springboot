package com.leo.dubbo.service;

import java.util.List;

import com.leo.dubbo.bean.UserAddress;

/**
 * 用户服务
 * @author lfy
 *
 */
public interface UserService {
	
	/**
	 * 按照用户id返回所有的收货地址
	 * @param userId
	 * @return
	 */
	public List<UserAddress> getUserAddressList(String userId);

}
