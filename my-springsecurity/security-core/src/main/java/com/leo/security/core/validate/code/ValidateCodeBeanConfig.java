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

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator(); 
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
//	@Bean
//	@ConditionalOnMissingBean(SmsCodeSender.class)
//	public SmsCodeSender smsCodeSender() {
//		return new DefaultSmsCodeSender();
//	}

}
