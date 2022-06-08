package com.lovsog;

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
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.text();
    }
}
