package com.xhpcd.client;

import com.xhpcd.message.*;
import com.xhpcd.protocol.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/13:23
 * @Description:
 */
@Slf4j
public class ChatClient2 {
    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean Login = new AtomicBoolean();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventExecutors);
         bootstrap.channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new MessageCodec());
                pipeline.addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("[[[[[[");
                        ByteBufAllocator alloc = ctx.alloc();//通过alloc申请的butebuf可以读取配置决定是池化还是直接内存，但是通过io的msg如果不经修饰就是使用的直接内存因为效率高
                        alloc.ioBuffer(1024);//确定调用直接内存

                        log.info("客户端收到信息--->{}",msg);
                    }
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        new Thread(()->{
                                 Scanner scanner = new Scanner(System.in);
                                 String username = "zhangsan";
                                while (true){
                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");
                                    String command = scanner.nextLine();
                                    String[] s = command.split(" ");
                                    switch (s[0]){
                                        case "send":
                                            System.out.println("ppppppp");
                                            ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                                            break;
                                        case "gcreate":
                                            Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                                            set.add(username); // 加入自己
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], set));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(username, s[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            return;
                                    }
                                }

                        }).start();

                    }


                });
            }
        });
        Channel channel = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync().channel();
        channel.closeFuture().sync();
        log.info("客户端关闭");
        eventExecutors.shutdownGracefully();
    }
}
