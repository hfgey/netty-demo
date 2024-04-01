package com.xhpcd.server.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/12:53
 * @Description:
 */
public abstract class UserServiceFactory {
    static  UserServiceMemoryImpl userServiceMemory;
    static {
        userServiceMemory = new UserServiceMemoryImpl();
    }
  public static UserService getUserService(){
       return userServiceMemory;
   }
}
