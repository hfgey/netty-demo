package com.xhpcd.message;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
public class ChatRequestMessage extends Message implements Serializable {
    private String username;
    private String toUsername;
    private String context;
    private String groupNmae;
    public ChatRequestMessage(String username,String toUsername,String context){
        this.username = username;
        this.toUsername = toUsername;
        this.context = context;
    }
    @Override
    public Integer getMessageType() {
        return ChatRequestMessage;
    }
}
