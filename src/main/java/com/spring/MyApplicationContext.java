package com.spring;

import java.io.File;
import java.net.URL;

/**
 * @author Lovsog
 * 2022/6/5
 * 容器类
 **/
public class MyApplicationContext {
    private Class configClass;

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 1. 解析配置类AppConfig
        // ComponentScan注解 --> 扫描路径 --> 扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();  //获得扫描路径 com.lovsog.service

        // 2. 扫描
        // 3种类加载器：
        // Bootstrap    ---> jre/lib
        // Ext          ---> jre/ext/lib
        // App          ---> classpath          ---> 当前应用类加载器对应的路径
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader(); // 获得App类加载器
        //classLoader.getResource("com/lovsog/service"); // 这里是相对路径，相对上面的classpath
        URL resource = classLoader.getResource("com/lovsog/service"); // 这里是相对路径，相对上面的classpath
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getAbsolutePath(); // 得到class绝对路径
                // 是.class文件才做处理
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class")); // 将绝对路径转换为 com.lovsog.service.UserService 的格式
                    className = className.replace("\\", ".");
                    try {
                        Class<?> clazz = classLoader.loadClass("");
                        // 判断得到的类上是否有Component注解，如果有，再继续操作
                        if (clazz.isAnnotationPresent(Component.class)) {
                            //..
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}
