package com.leo.springboot.enable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @Enable*注解开启一些功能的原理
 *                     通过@Enable*注解加入Bootstrap启动类上。当Spring启动，
 *                     会执行selectImports/registerBeanDefinitions(AnnotationMetadata annotationMetadata)方法，
 *                     在这个方法中我们做了某些处理，使得和@Enable*搭配使用的注解生效
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MyImportBeanDefinitionRegistrar.class)
public @interface EnableEcho{

    /**
     * 传入包名
     * 
     * @return
     */
    String[] packages() default "";
}
