# 2. Spring 启动与扫描解析的模拟实现



在创建容器时，会通过配置类来注册bean

```java
MyApplicationContext applicationContext = new MyApplicationContext(AppConfig.class);
```

在配置类 `AppConfig.class` 中，通过 `@ComponentScan` 来设置要扫描的bean所在的位置

```java
package com.lovsog;

import com.spring.ComponentScan;

@ComponentScan("com.lovsog.service")
public class AppConfig {

}
```

以下是 `@ComponentScan` 的代码

```java
package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {

    String value() default "";  //默认值为空

}
```



在 com.lovsog包 下创建 service包 ，在 service包 中创建 `UserService.java` 

```java
package com.lovsog.service;

import com.spring.Component;

@Component("userService")
public class UserService {

}
```

其中，`@Component` 用来标记一个类为Spring容器的Bean，其代码如下所示

```java
package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    String value() default "";

}
```



完成上述操作以后，接下来在容器中**解析配置类AppConfig**和**扫描bean**的操作，具体如下

```java
package com.spring;

import java.io.File;
import java.net.URL;

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
        //      Bootstrap    ---> jre/lib
        //      Ext          ---> jre/ext/lib
        //      App          ---> classpath          ---> 当前应用类加载器对应的路径
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
```

