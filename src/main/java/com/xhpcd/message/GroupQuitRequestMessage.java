package com.xhpcd.message;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
public class GroupQuitRequestMessage extends Message{
    private String groupName;
    private String username;
    public GroupQuitRequestMessage(String username,String groupName){
        this.username = username;
        this.groupName = groupName;
    }
    @Override
    public Integer getMessageType() {
        return GroupQuitRequestMessage;
    }
}
