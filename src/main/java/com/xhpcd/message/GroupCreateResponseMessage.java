package com.xhpcd.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:11
 * @Description:
 */
@Data
@AllArgsConstructor
public class GroupCreateResponseMessage extends Message{
    private String context;
    private boolean isOk;
    @Override
    public Integer getMessageType() {
        return GroupCreateRequestMessage;
    }
}
