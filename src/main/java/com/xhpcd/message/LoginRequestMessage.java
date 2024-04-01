package com.xhpcd.message;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:09
 * @Description:
 */
@Data
public class LoginRequestMessage extends Message{
    private String name;
    private String password;
    public LoginRequestMessage(String name,String password){
        this.name = name;
        this.password = password;
    }

    @Override
    public Integer getMessageType() {
        return LoginRequestMessage;
    }
}
