package com.xhpcd.server.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/12/9:12
 * @Description:
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return name+" 你好";
    }
}
