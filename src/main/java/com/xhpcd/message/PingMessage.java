package com.xhpcd.message;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/11:17
 * @Description:
 */
@Data
public class PingMessage extends Message {
    private String Ping = "ping";

    @Override
    public Integer getMessageType() {
        return PingMessage;
    }
}
