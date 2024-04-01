package com.xhpcd.server.session;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/8:44
 * @Description:
 */
public class GroupSessionFactory {
    static GroupSessionMemoryImpl groupSessionMemory= new GroupSessionMemoryImpl();
    public static GroupSessionMemoryImpl getSessionGroup(){
        return groupSessionMemory;
    }
}
