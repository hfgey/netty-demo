package com.xhpcd.message;

import lombok.Data;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
public class GroupCreateRequestMessage extends Message{
    String GroupName;
    Set<String> members;
    public GroupCreateRequestMessage(String GroupName, Set<String> members){
        this.GroupName = GroupName;
        this.members = members;

    }
    @Override
    public Integer getMessageType() {
        return GroupCreateRequestMessage;
    }
}
