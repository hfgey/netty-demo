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
public class GroupQuitResponseMessage extends Message{
    @Override
    public Integer getMessageType() {
        return GroupQuitResponseMessage;
    }
}
