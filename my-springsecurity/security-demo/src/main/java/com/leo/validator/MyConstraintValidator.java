/**
 * 
 */
package com.leo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.leo.service.HelloService;

/**
 * @author zhailiang
 * 实现ConstraintValidator注解的类，Spring会自动加载到容器，不用配置@Component注解，因此可以用@Autowired为其注入Bean
 * ConstraintValidator第一个泛型是要处理的注解，第二个参数是注解的字段类型
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

	@Autowired
	private HelloService helloService;
	
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		System.out.println("my validator init");
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		helloService.greeting("tom");
		System.out.println(value);
		//return false;
		return true;
	}

}
