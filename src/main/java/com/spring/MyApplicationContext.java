package com.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lovsog
 * 2022/6/5
 * 容器类
 **/
public class MyApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>(); // 单例池
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); // bean池

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 解析配置类
        // ComponentScan注解 ---> 扫描路径 ---> 扫描 ---> BeanDefinition ---> BeanDefinitionMap
        scan(configClass);

        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanName, beanDefinition); // 单例Bean
                singletonObjects.put(beanName, bean);
            }
        }

    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {

        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // 依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true); // 取消 Java 语言访问检查
                    declaredField.set(instance, bean);
                }
            }

            if (instance instanceof BeanNameAware) {
                ((BeanNameAware)instance).setBeanName(beanName);
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void scan(Class configClass) {
        // 1. 解析配置类AppConfig
        // ComponentScan注解 --> 扫描路径 --> 扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();  //获得扫描路径 com.lovsog.service
        path = path.replace(".", "/");

        // 2. 扫描
        // 3种类加载器：
        //      Bootstrap    ---> jre/lib
        //      Ext          ---> jre/ext/lib
        //      App          ---> classpath          ---> 当前应用类加载器对应的路径
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader(); // 获得App类加载器
        //classLoader.getResource("com/lovsog/service"); // 这里是相对路径，相对上面的classpath
        URL resource = classLoader.getResource(path); // 这里是相对路径，相对上面的classpath
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
                        Class<?> clazz = classLoader.loadClass(className);
                        // 判断得到的类上是否有Component注解，如果有，再继续操作
                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 表示当前类是一个Bean
                            // 解析类：判断当前bean是单例bean，还是原型bean，还是....（bean的作用域）
                            //          解析出一个类 ---> BeanDefinition
                            // BeanDefinition

                            Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnnotation.value();

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(clazz); // 将对象存入
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                // 如果有Scope注解，将其value设置为beanDefinition的作用域
                                Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                // 如果没有Scope注解，设置作用域为singleton
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object o = singletonObjects.get(beanName);
                return o;
            } else {
                // 创建Bean对象
                Object bean = createBean(beanName, beanDefinition);
                return bean;
            }
        } else {
            // 不存在对应的Bean
            throw new NullPointerException();
        }
    }
}
