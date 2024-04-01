package com.xhpcd.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/21:56
 * @Description:
 */
@Data
public abstract class Message implements Serializable {


    private int sequenceId;
    private int messageType;
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;
    public abstract Integer getMessageType();
    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int PingMessage = 14;
    public static final Map<Integer,Class<?>> messageClass = new HashMap<>();
    static {
        messageClass.put(0,com.xhpcd.message.LoginRequestMessage.class);
        messageClass.put(1, com.xhpcd.message.LoginResponseMessage.class);
        messageClass.put(2,com.xhpcd.message.ChatRequestMessage.class);
        messageClass.put(3,com.xhpcd.message.ChatResponseMessage.class);
        messageClass.put(4,com.xhpcd.message.GroupCreateRequestMessage.class);
        messageClass.put(5,com.xhpcd.message.GroupCreateResponseMessage.class);
        messageClass.put(6,com.xhpcd.message.GroupJoinRequestMessage.class);
        messageClass.put(7,com.xhpcd.message.GroupJoinResponseMessage.class);
        messageClass.put(8,com.xhpcd.message.GroupQuitRequestMessage.class);
        messageClass.put(9,com.xhpcd.message.GroupQuitResponseMessage.class);
        messageClass.put(10,com.xhpcd.message.GroupChatRequestMessage.class);
        messageClass.put(11,com.xhpcd.message.GroupChatResponseMessage.class);
        messageClass.put(12, com.xhpcd.message.GroupMembersRequestMessage.class);
        messageClass.put(13,com.xhpcd.message.GroupMembersResponseMessage.class);
        messageClass.put(14,com.xhpcd.message.PingMessage.class);
        messageClass.put(101, RpcRequestMessage.class);
        messageClass.put(102, RpcResponseMessage.class);
    }
}
