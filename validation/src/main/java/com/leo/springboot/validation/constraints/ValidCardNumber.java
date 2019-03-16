package com.leo.springboot.validation.constraints;

import com.leo.springboot.validation.ValidCardNumberConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidCardNumberConstraintValidator.class})
public @interface ValidCardNumber {
    // ValidationMessages 中的配置在web下可以，但是webflux不行
    // 通过edit的find in path 查看别的实现的配置，在hibernate-validation中
    String message() default "{com.leo.springboot.validation.constraints.ValidCardNumber.message}";

    // 分组校验 --> 规范能想到的
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
