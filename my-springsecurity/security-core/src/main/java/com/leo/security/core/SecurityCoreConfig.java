package com.leo.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.leo.security.core.properties.SecurityProperties;

/**
 * 接口安全配置核心模块
 * 
 * @author LSH7120
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig{

}
