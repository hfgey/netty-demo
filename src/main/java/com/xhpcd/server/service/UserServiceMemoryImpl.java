package com.xhpcd.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/12:55
 * @Description:
 */
public class UserServiceMemoryImpl implements UserService{
    private static Map<String,String> map = new ConcurrentHashMap<>();

    static {
        map.put("zhangsan","123");
        map.put("lisi","123");
        map.put("wangwu","123");
    }
    @Override
    public boolean login(String name, String pwd) {
        String p = map.get(name);
        if(p == null){
            return false;
        }
        return (p.equals(pwd));
    }
}
