package com.xhpcd.message;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:10
 * @Description:
 */

import lombok.Data;

@Data
public class LoginResponseMessage extends Message{
    private boolean login;

    private String status;
    public LoginResponseMessage(boolean b,String s){
        login = b;
        status =s;
    }
    public boolean getLogin(){
        return login;
    }


    @Override
    public Integer getMessageType() {
        return LoginResponseMessage;
    }
}
