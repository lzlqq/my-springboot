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
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全模块默认的配置。
 */
@Configuration
public class ValidateCodeBeanConfig{

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码图片生成器
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator") //既可以配ConfigBean，也可以在类上加@Component注解，不过那样不一定会扫到包
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 短信验证码发送器
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class) // 既可以配名字，也可以配接口类，两种配置方式
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }

}
