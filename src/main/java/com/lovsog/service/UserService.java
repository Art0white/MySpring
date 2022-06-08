package com.lovsog.service;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.Scope;

/**
 * @author Lovsog
 * 2022/6/5
 **/
@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;
    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public void text(){
        System.out.println(orderService);
        System.out.println(beanName);
    }


}
