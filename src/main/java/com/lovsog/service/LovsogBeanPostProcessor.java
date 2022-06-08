package com.lovsog.service;

import com.lovsog.service.UserService;
import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * @author Lovsog
 * 2022/6/8
 **/
@Component
public class LovsogBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // 初始化前自定义操作
        System.out.println("初始化前");
        if (beanName.equals("userService")) {
            ((UserService)bean).setName("lovsog");
        }
        return bean;
    }

    //
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // 初始化后自定义操作
        System.out.println("初始化后");
        return bean;
    }
}
