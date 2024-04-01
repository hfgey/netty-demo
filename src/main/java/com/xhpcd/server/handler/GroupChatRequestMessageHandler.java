package com.xhpcd.server.handler;

import com.xhpcd.message.GroupChatRequestMessage;
import com.xhpcd.message.GroupChatResponseMessage;
import com.xhpcd.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/8:35
 * @Description:
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupChatRequestMessage grm) throws Exception {
        String username = grm.getUsername();
        String groupNmae = grm.getGroupNmae();
        String context = grm.getContext();
        List<Channel> membersChannel = GroupSessionFactory.getSessionGroup().getMembersChannel(groupNmae);
        for (Channel c:
             membersChannel) {
            c.writeAndFlush(new GroupChatResponseMessage(username,context));
        }
    }
}
