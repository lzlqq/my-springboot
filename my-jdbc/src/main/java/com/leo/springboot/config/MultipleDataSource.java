package com.leo.springboot.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
public class MultipleDataSource {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test1")
                .username("root")
                .password("123456")
                .build();
        return dataSource;
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test1")
                .username("root")
                .password("123456")
                .build();
        return dataSource;
    }
}
