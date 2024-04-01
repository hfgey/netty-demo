package com.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/05/19:59
 * @Description: 用协议的方式发送信息给redis
 */
public class redis {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        byte[] ch = {13,10};
        Bootstrap bo = new Bootstrap().group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes("*3".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("$3".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("set".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("$4".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("name".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("$5".getBytes());
                                buffer.writeBytes(ch);
                                buffer.writeBytes("nihao".getBytes());
                                ctx.writeAndFlush(buffer);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                });

        ChannelFuture channelFuture = bo.connect(new InetSocketAddress("192.168.88.130", 6379)).sync();
       // channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("AUTH " + "hou13579o" + "\r\n", Charset.defaultCharset()));
    }
}
