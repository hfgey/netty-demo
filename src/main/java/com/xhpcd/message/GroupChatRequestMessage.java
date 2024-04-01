package com.xhpcd.message;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:12
 * @Description:
 */
@Data
public class GroupChatRequestMessage extends Message{
    private String username;
    private String context;
    private String groupNmae;
    public GroupChatRequestMessage(String username,String groupName ,String context){
        this.username = username;
        this.context = context;
        this.groupNmae = groupName;
    }
    @Override
    public Integer getMessageType() {
        return GroupCreateRequestMessage;
    }
}
