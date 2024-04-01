package com.xhpcd.server.handler;

import com.xhpcd.message.LoginRequestMessage;
import com.xhpcd.message.LoginResponseMessage;
import com.xhpcd.server.service.UserServiceFactory;
import com.xhpcd.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/18:51
 * @Description:
 *
 * boolean类型的属性值不建议设置为is开头，否则会引起rpc框架的序列化异常。
 *
 * 2、如果强行将IDE自动生成的isSuccess()方法修改成getSuccess()，也能获取到Success属性值，若两者并存，则之后通过getSuccess()方法获取Success属性值。
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage login) throws Exception {

        String name = login.getName();
        String password = login.getPassword();
        boolean lo = UserServiceFactory.getUserService().login(name, password);
        LoginResponseMessage responseMessage;
        if(lo){
            SessionFactory.getSession().bind(ctx.channel(),name);
            responseMessage = new LoginResponseMessage(lo,"登录成功");
        }else {
            responseMessage = new LoginResponseMessage(lo,"登录失败");
        }
        ctx.writeAndFlush(responseMessage);
    }
}
