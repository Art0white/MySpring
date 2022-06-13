package com.lovsog.service;

import com.lovsog.service.UserService;
import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        // 匹配
        if (beanName.equals("userService")) {

            //生成代理对象
            Object proxyInstance = Proxy.newProxyInstance(LovsogBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 执行代理逻辑
                    System.out.println("代理逻辑"); // 找切点
                    // 执行业务逻辑
                    return method.invoke(bean, args);
                }
            });
            // 返回代理对象
            return proxyInstance;
        }
        return bean;
    }
}
