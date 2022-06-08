package com.spring;

/**
 * @author Lovsog
 * 2022/6/8
 * 将BeanName回调的接口
 **/
public interface BeanNameAware {
    void setBeanName(String name);
}
