package com.xhpcd.server.handler;

import com.xhpcd.message.RpcRequestMessage;
import com.xhpcd.message.RpcResponseMessage;
import com.xhpcd.server.service.HelloService;
import com.xhpcd.server.service.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/12/8:50
 * @Description:
 */
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage rpc)  {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        try {
            String interfaceName = rpc.getInterfaceName();
            HelloService object = (HelloService) ServiceFactory.getObject(Class.forName(interfaceName));
            Class<? extends HelloService> claszz = object.getClass();
            Method method = claszz.getMethod(rpc.getMethodName(), rpc.getParameterTypes());
            Object invoke = method.invoke(object, rpc.getParameterValue());
            rpcResponseMessage.setReturnValue(invoke);
            ctx.writeAndFlush(rpcResponseMessage);
        }catch (Exception e){
            rpcResponseMessage.setExceptionValue(e);
            ctx.writeAndFlush(rpcResponseMessage);
        }
    }
}
