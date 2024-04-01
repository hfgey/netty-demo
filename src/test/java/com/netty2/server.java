package com.netty2;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/04/20:34
 * @Description:
 */
@Slf4j
public class server {
    public static void main(String[] args) {
        NioEventLoopGroup nio = new NioEventLoopGroup();

        new ServerBootstrap().group(nio)
                .channel(NioServerSocketChannel.class)

                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast(new StringDecoder());

                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("msg{}",msg);
                                System.out.println(msg);
                               super.channelRead(ctx,msg);//把信息传递给下一个handler唤醒下一个入站处理器
                            }
                        });

                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("2msg{}",msg);
                                System.out.println(msg);
                                //没必要唤醒出站处理器
                                super.channelRead(ctx,msg);
                               // nioSocketChannel.write(ctx.alloc().buffer().writeBytes("kjkjk".getBytes()));从tail向前找，找到出站的
                               // ctx.write(ctx.alloc().buffer().writeBytes("kjkjk".getBytes()));从当前向前找，因为没有出站的所以不触发
                                ByteBuf buffer = ctx.alloc().buffer();

                                buffer.writeBytes(new byte[]{'h','e','l','l','o'});
                                //数据写回
                                ctx.writeAndFlush(buffer);
                            }
                        });
//                        pipeline.addLast(new ChannelOutboundHandlerAdapter(){
//                            @Override
//                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//                                System.out.println("1");
//                                System.out.println("2");
//                                super.write(ctx,msg,promise);
//                            }
//                        });

                    }
                }).bind(8080);
    }
}
