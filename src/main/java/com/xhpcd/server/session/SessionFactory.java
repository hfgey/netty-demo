package com.xhpcd.server.session;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/18:42
 * @Description:
 */
public class SessionFactory {
    static SessionMemoryImpl sessionMemory= new SessionMemoryImpl();
   public static Session getSession(){
        return sessionMemory;
    }
}
