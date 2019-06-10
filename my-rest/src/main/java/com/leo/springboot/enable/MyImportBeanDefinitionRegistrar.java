package com.leo.springboot.enable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,BeanDefinitionRegistry registry){
        //获取EnableEcho注解的所有属性的value
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableEcho.class.getName());

        //获取package属性的value
        List<String> packages = Arrays.asList((String[]) attributes.get("packages"));

        //EchoBeanPostProcessor对象将BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(EchoBeanPostProcessor.class);

        //给EchoBeanPostProcessor.class的packages属性加入值
        beanDefinitionBuilder.addPropertyValue("packages", packages);

        //使用beanDefinitionRegistry对象将EchoBeanPostProcessor注入至Spring容器中
        registry.registerBeanDefinition(EchoBeanPostProcessor.class.getName(), beanDefinitionBuilder.getBeanDefinition());
    }

}
