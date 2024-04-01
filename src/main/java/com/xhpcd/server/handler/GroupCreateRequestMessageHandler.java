package com.xhpcd.server.handler;

import com.xhpcd.message.GroupChatResponseMessage;
import com.xhpcd.message.GroupCreateRequestMessage;
import com.xhpcd.message.GroupCreateResponseMessage;
import com.xhpcd.server.session.Group;
import com.xhpcd.server.session.GroupSessionFactory;
import com.xhpcd.server.session.GroupSessionMemoryImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/8:36
 * @Description:
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage grm) throws Exception {
        String groupName = grm.getGroupName();
        Set<String> members = grm.getMembers();
        GroupSessionMemoryImpl sessionGroup = GroupSessionFactory.getSessionGroup();
        Group group = sessionGroup.createGroup(groupName, members);
        if(group == null){
            ctx.writeAndFlush(new GroupCreateResponseMessage("群聊已存在",false));
            return;
        }
        List<Channel> membersChannel = sessionGroup.getMembersChannel(groupName);
        for (Channel c : membersChannel) {
            c.writeAndFlush(new GroupCreateResponseMessage("您已被拉入群聊",true));
        }


    }
}
