package com.xhpcd.message;

import lombok.Data;

import javax.sql.rowset.serial.SerialStruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
public class GroupJoinRequestMessage extends Message{
    private String username;
    private String groupName;
    public GroupJoinRequestMessage(String username,String groupName){
        this.username = username;
        this.groupName = groupName;
    }
    @Override
    public Integer getMessageType() {
        return GroupJoinRequestMessage;
    }
}
