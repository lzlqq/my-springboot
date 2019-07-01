package com.leo.springboot.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.leo.springboot.api.DemoService;

/**
 * DemoServiceImpl
 * 服务提供类
 * @author xiaoze
 * @date 2018/6/7
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
