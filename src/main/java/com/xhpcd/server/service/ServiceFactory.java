package com.xhpcd.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/12/9:15
 * @Description:
 */
public class ServiceFactory {
    static Properties properties;
    static ConcurrentHashMap<Class<?>,Object> map = new ConcurrentHashMap<>();
    static {
        InputStream resourceAsStream = ServiceFactory.class.getResourceAsStream("/xhpcd.properties");
        try {
            properties = new Properties();
            properties.load(resourceAsStream);
            Set<String> strings = properties.stringPropertyNames();
            for (String string : strings) {
                if(string.endsWith("Service")){
                    Class<?> clazz = Class.forName(string);
                    Class<?> aClass = Class.forName((String) properties.get(string));
                    map.put(clazz,aClass.newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   public static Object getObject(Class s){
        return map.get(s);
    }
}
