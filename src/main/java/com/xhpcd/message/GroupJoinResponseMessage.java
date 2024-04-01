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
public class GroupJoinResponseMessage extends Message{
    @Override
    public Integer getMessageType() {
        return GroupJoinResponseMessage;
    }
}
