package com.spring;

/**
 * @author Lovsog
 * 2022/6/7
 * 一个Bean的相关定义
 **/


public class BeanDefinition {
    private Class clazz;    //类型
    private String scope;   //作用域

//    public BeanDefinition(Class clazz, String scope) {
//        this.clazz = clazz;
//        this.scope = scope;
//    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
