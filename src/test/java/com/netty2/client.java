package com.netty2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/04/20:34
 * @Description:
 */
@Slf4j
public class client {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventGroup = new NioEventLoopGroup();


        ChannelFuture channelFuture = new Bootstrap().group(eventGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new StringEncoder());

                   /*     pipeline.addLast(new ChannelOutboundHandlerAdapter(){
                       @Override
                       public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                           ctx.channel().writeAndFlush("ll");
                        }
                      });*/
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("客户端:"+msg);
                                ByteBuf byteBuf = (ByteBuf)msg;

                                System.out.println(byteBuf.toString(Charset.defaultCharset()));

                            }
                        });
                    }
                }).connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();


        new Thread(()->{
            Scanner sc = new Scanner(System.in);
            while (true){
                String next = sc.next();
                if(next == "quit"){
                    channel.close();//这里的关闭是另一个线程异步进行
                    break;
                }

                channel.writeAndFlush(next);

            }
        }).start();
        ChannelFuture closeFuture = channel.closeFuture();

        ChannelFuture sync = closeFuture.sync();//阻塞当channel关闭后进行一些操作
        log.info("管道已经关闭" );
        //此时如果不进行操作程序还是不会停止因为NioEventLoopGroup还没关闭
        eventGroup.shutdownGracefully();

    }
}
