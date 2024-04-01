package com.xhpcd.protocol;

import com.xhpcd.config.Config;
import com.xhpcd.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:18
 * @Description:保持2的正数次方
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        //魔数
        byteBuf.writeBytes(new byte[]{'x','h','p','c','d'});
        //版本号
        byteBuf.writeByte(1);
        //序列化算法 0 jdk 1 json
        byteBuf.writeByte(Config.getAlgorithm());
        //指令类型 登录 注册 退出等
        byteBuf.writeByte(message.getMessageType());
        //请求序号 实现异步全双工
        byteBuf.writeInt(message.getSequenceId());
        //正文长度
        //正文内容
        byte[] serialize = Config.getDefault().serialize(message);
        byteBuf.writeInt(serialize.length);
        byteBuf.writeBytes(serialize);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        ByteBuf magicNum = byteBuf.readBytes(new byte[5], 0, 5);
        byte version = byteBuf.readByte();
        byte serize = byteBuf.readByte();
        byte type = byteBuf.readByte();
        int SequenceId = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes,0,length);
        Object o = null;
        Serializer.Algorithm[] values = Serializer.Algorithm.values();
        int i = type;
        o = values[serize].deserialize(Message.messageClass.get(i),bytes);
        list.add(o);
        log.info("长度{}",length);

    }
}
