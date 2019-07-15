package com.leo.springboot.sharding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/add")
    public void addOrder() {
        OrderEntity entity10 = new OrderEntity();
        entity10.setOrderId(10000L);
        entity10.setOrderNo("No1000000");
        entity10.setUserId(102333001L);
        orderMapper.insertSelective(entity10);

        OrderEntity entity11 = new OrderEntity();
        entity11.setOrderId(10001L);
        entity11.setOrderNo("No1000000");
        entity11.setUserId(102333000L);
        orderMapper.insertSelective(entity11);
    }
}