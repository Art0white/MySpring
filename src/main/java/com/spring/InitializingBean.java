package com.spring;

/**
 * @author Lovsog
 * 2022/6/8
 * 初始化机制接口
 **/
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
