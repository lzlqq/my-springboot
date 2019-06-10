package com.leo.springboot.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    private final DataSource masterDataSource;

    private final DataSource slaveDataSource;

    /**
     * 1.没加Qualifier的dataSource,此时默认会加载@Primary注解的DataSource,如果没加@Primary注解，会报错
     * 2.构造器注入时不用加@Autowired注解
     */
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.slaveDataSource = slaveDataSource;
    }
}
