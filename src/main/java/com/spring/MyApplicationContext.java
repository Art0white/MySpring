package com.spring;

/**
 * @author Lovsog
 * 2022/6/5
 * 容器类
 **/
public class MyApplicationContext {
    private Class configClass;

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 解析配置类AppConfig
        // ComponentScan注解 --> 扫描路径 --> 扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();  //获得扫描路径
        System.out.println(path);

        // 扫描
        //
    }

    public Object getBean(String beanName) {
        return null;
    }
}
