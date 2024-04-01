package com.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/05/21:04
 * @Description:
 */
public class http {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap().group(eventExecutors).channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new LoggingHandler());
                nioSocketChannel.pipeline().addLast(new HttpServerCodec());//http协议编解码器
                nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {

                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                        byte[] bytes = "<h1>hello</h1>".getBytes();
                        response.headers().setInt(CONTENT_LENGTH,bytes.length);
                        response.content().writeBytes(bytes);
                        channelHandlerContext.writeAndFlush(response);
                    }
                });
              /*  nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(msg.getClass());
                        if(msg instanceof HttpRequest){
                           HttpRequest httpRequest = (HttpRequest)msg;
                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.getProtocolVersion(), HttpResponseStatus.OK);
                            response.content().writeBytes("<h1>hello</h1>".getBytes());
                        }else if (msg instanceof HttpContent){

                        }
                    }
                });*/
            }
        });
        bootstrap.bind(8080).sync();

    }
}
