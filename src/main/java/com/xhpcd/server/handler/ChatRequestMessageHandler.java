package com.xhpcd.server.handler;

import com.xhpcd.message.ChatRequestMessage;
import com.xhpcd.message.ChatResponseMessage;
import com.xhpcd.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/19:00
 * @Description:
 */
@Slf4j
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage chatRequestMessage) throws Exception {

        String context = chatRequestMessage.getContext();
        String username = chatRequestMessage.getUsername();
        String toUsername = chatRequestMessage.getToUsername();
        Channel channel1 = SessionFactory.getSession().getChannel(toUsername); //to用户的channel
        System.out.println("------>已经读到");
        if(channel1 == null){
            log.info("发送者{}，没收到信息",chatRequestMessage.getUsername());
            ctx.writeAndFlush(new ChatResponseMessage(username,false,"用户不在线"));
        }else {
            log.info("发送者{}，接到信息",chatRequestMessage.getUsername());
            channel1.writeAndFlush(new ChatResponseMessage(username,true,context));
        }
    }
}
