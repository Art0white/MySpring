package com.lovsog;

import com.lovsog.service.UserInterface;
import com.lovsog.service.UserService;
import com.spring.Autowired;
import com.spring.MyApplicationContext;

/**
 * @author Lovsog
 * 2022/6/5
 **/
public class Text {
    public static void main(String[] args) {
        MyApplicationContext applicationContext = new MyApplicationContext(AppConfig.class);

        // Object userService = applicationContext.getBean("userService");     // map <beanName, bean对象>  <---单例池
        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
        userService.test(); // 1. 执行代理逻辑    2. 业务test
    }
}
