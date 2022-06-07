package com.lovsog.service;

import com.spring.Autowired;
import com.spring.Component;
import com.spring.Scope;

/**
 * @author Lovsog
 * 2022/6/5
 **/
@Component("userService")
@Scope("prototype")
public class UserService {

    @Autowired
    private OrderService orderService;

    public void text(){
        System.out.println(orderService);
    }

}
