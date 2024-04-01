package com.xhpcd.server;

import com.xhpcd.message.ChatResponseMessage;
import com.xhpcd.message.GroupChatRequestMessage;
import com.xhpcd.message.LoginRequestMessage;
import com.xhpcd.message.LoginResponseMessage;
import com.xhpcd.protocol.MessageCodec;
import com.xhpcd.server.handler.*;
import com.xhpcd.server.service.UserServiceFactory;
import com.xhpcd.server.session.SessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/13:31
 * @Description:
 */
@Slf4j
public class ChatServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup emp  = new NioEventLoopGroup();
        ChatRequestMessageHandler Chat_handler = new ChatRequestMessageHandler();
        LoginRequestMessageHandler Login_handler = new LoginRequestMessageHandler();
        GroupCreateRequestMessageHandler groupCreateRequestMessageHandler = new GroupCreateRequestMessageHandler();
        GroupChatRequestMessageHandler groupChatRequestMessageHandler = new GroupChatRequestMessageHandler();
        ServerBootstrap bootstrap = new ServerBootstrap();
        QuitHandler quitHandler = new QuitHandler();

        bootstrap.group(boss,emp).channel(NioServerSocketChannel.class).
                childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        //用来检测客户端是否假死
                        pipeline.addLast(new IdleStateHandler(13,0,0));
                        pipeline.addLast(new ChannelDuplexHandler(){

                            //即是入站也是出站处理器
                            //用来触发特定事件


                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                IdleStateEvent idl = (IdleStateEvent)evt;
                                if(idl.state() == IdleState.READER_IDLE){
                                    log.info("客户端5s未发送信息");
                                    ctx.channel().close();
                                }
                            }
                        });
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                        pipeline.addLast(new LoggingHandler());
                        pipeline.addLast(new MessageCodec());
                        pipeline.addLast(quitHandler);
                        pipeline.addLast(Login_handler);
                        pipeline.addLast(Chat_handler);
                        pipeline.addLast(groupCreateRequestMessageHandler);
                        pipeline.addLast(groupChatRequestMessageHandler);

                    }
                });
        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(8080)).sync();
        channelFuture.channel().closeFuture().sync();
        log.info("服务端关闭");
        boss.shutdownGracefully();
        emp.shutdownGracefully();
    }

}
