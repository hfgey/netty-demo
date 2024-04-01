package com.xhpcd.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.sql.rowset.serial.SerialStruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:12
 * @Description:
 */
@Data
@AllArgsConstructor
public class GroupChatResponseMessage extends Message{
    private String from;
    private String context;
    @Override
    public Integer getMessageType() {
        return GroupChatResponseMessage;
    }
}
