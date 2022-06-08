package com.spring;

/**
 * @author Lovsog
 * 2022/6/8
 * Bean的后置处理器
 **/
public interface BeanPostProcessor {
    // 初始化前
    Object postProcessBeforeInitialization(Object bean, String beanName);
    // 初始化后
    Object postProcessAfterInitialization(Object bean, String beanName);
}
