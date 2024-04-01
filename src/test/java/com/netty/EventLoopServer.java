package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/04/10:09
 * @Description:
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {

       /* new ServerBootstrap().group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                    }
                }).bind(8080);*/
        main1(null);
    }
    public static void main1(String[] args) {
        //boss 负责ServerSocketChannel的连接 worker负责具体的read write  应该是主从模式的实现
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        EventLoop next = eventExecutors.next();

        ServerBootstrap channel = new ServerBootstrap().group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class);

        channel.childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();


                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



                                ByteBuf byteBuf = (ByteBuf)msg;
                                log.info("{}", byteBuf.toString(Charset.defaultCharset()));
                                pipeline.writeAndFlush("pp");
                                ctx.writeAndFlush("pp");
                                super.channelRead(ctx,msg);
                            }
                        });
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                System.out.println("lll");
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
