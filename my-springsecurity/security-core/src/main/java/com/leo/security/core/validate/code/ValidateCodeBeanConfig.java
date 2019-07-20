/**
 * 
 */
package com.leo.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leo.security.core.properties.SecurityProperties;
import com.leo.security.core.validate.code.image.ImageCodeGenerator;
import com.leo.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.leo.security.core.validate.code.sms.SmsCodeSender;

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")//既可以配ConfigBean，也可以在类上加@Component注解，不过那样不一定会扫到包
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator(); 
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)// 既可以配名字，也可以配接口类，两种配置方式
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}
