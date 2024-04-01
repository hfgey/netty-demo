package com.xhpcd.message;

import lombok.Data;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
public class ChatResponseMessage extends Message{
    String username;
    private String context;
    private boolean isSucess;
    public ChatResponseMessage(String username ,boolean isSucess,String context){
        this.context = context;
        this.username = username;
        this.isSucess = isSucess;
    }
    @Override
    public Integer getMessageType() {
        return ChatResponseMessage;
    }
}
