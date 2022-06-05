package com.lovsog;

import com.spring.MyApplicationContext;

/**
 * @author Lovsog
 * 2022/6/5
 **/
public class Text {
    public static void main(String[] args) {
        MyApplicationContext applicationContext = new MyApplicationContext(AppConfig.class);
        Object userService = applicationContext.getBean("userService");

    }
}
