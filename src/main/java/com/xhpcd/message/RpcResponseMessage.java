package com.xhpcd.message;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/12/8:17
 * @Description:
 */
@Data
public class RpcResponseMessage extends Message{
    //结果返回
    private Object returnValue;
    //异常
    private Exception exceptionValue;


    @Override
    public Integer getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}
