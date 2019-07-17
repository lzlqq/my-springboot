package com.leo.springboot.sharding.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

@Configuration
public class DataSourceConfig{

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean(name = "shardingDataSource",destroyMethod = "close")
    @Qualifier("shardingDataSource")
    public DataSource getShardingDataSource(){
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(3);

        // 配置第一个数据源
        DruidDataSource dataSource1 = createDefaultDruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/db0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("db0", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = createDefaultDruidDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/db1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSource2.setName("db1-0001");
        dataSourceMap.put("db1", dataSource2);

        // 配置第三个数据源
        DruidDataSource dataSource3 = createDefaultDruidDataSource();
        dataSource3.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource3.setUrl("jdbc:mysql://localhost:3306/db2");
        dataSource3.setUsername("root");
        dataSource3.setPassword("123456");
        dataSourceMap.put("db2", dataSource3);

        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("db${0..2}.t_order_${0..1}");
        //orderTableRuleConfig.setActualDataNodes("db0.t_order_0,db0.t_order_1,db1.t_order_0,db1.t_order_1,db2.t_order_0,db2.t_order_1");

        // 配置分库策略（Groovy表达式配置db规则）
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "db${user_id % 3}"));

        // 配置分表策略（Groovy表达式配置表路由规则）
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // 配置order_items表规则...

        // 获取数据源对象
        DataSource dataSource = null;
        try{
            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dataSource;
    }

    private DruidDataSource createDefaultDruidDataSource(){
        return (DruidDataSource) DataSourceBuilder.create().build();
    }
    
}
