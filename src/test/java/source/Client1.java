package source;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2024/03/27/19:12
 * @Description:
 */
public class Client1 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
         bootstrap.channel(NioSocketChannel.class).group(eventExecutors)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator();
                                ByteBuf byteBuf = pooledByteBufAllocator.directBuffer(20);
                                byteBuf.writeBytes(Charset.defaultCharset().encode("111111111111111111\n"));
                                pipeline.addLast(new LoggingHandler());
                                pipeline.writeAndFlush(byteBuf);
                            }


                        });


                    }
                });
        Channel localhost = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync().channel();

    }
}
