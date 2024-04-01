package com.xhpcd.protocol;

import com.xhpcd.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/09/22:44
 * @Description:
 */
public class Test {
    public static void main(String[] args) throws Exception {

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new MessageCodec());

        LoginRequestMessage message = new LoginRequestMessage("张三","123");
        embeddedChannel.writeOutbound(message);
        MessageCodec messageCodec = new MessageCodec();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);
        messageCodec.encode(null,message,buffer);

        embeddedChannel.writeInbound(buffer);
    }
}
