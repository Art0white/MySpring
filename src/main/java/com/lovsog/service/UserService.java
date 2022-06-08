package com.lovsog.service;

import com.spring.*;

/**
 * @author Lovsog
 * 2022/6/5
 **/
@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;
    private String beanName;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化");
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public void text(){
        System.out.println(orderService);
        System.out.println(beanName);
        System.out.println(name);
    }
}
