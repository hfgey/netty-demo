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
public class GroupMembersRequestMessage extends Message{
    private String groupName;
    public GroupMembersRequestMessage(String groupName){
        this.groupName = groupName;
    }
    @Override
    public Integer getMessageType() {
        return GroupMembersRequestMessage;
    }
}
