package com.xhpcd.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/12/8:18
 * @Description:
 */
@Data
@AllArgsConstructor
public class RpcRequestMessage extends Message{
    private String interfaceName;//远程调用接口名
    private String methodName;//方法名
    private Class<?> returnType;
    //方法参数类型
    private Class[] parameterTypes;
    private Object[] parameterValue;


    @Override
    public Integer getMessageType() {
        return RPC_MESSAGE_TYPE_REQUEST;
    }
}
