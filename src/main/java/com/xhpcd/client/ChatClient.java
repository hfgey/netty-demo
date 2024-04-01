package com.xhpcd.client;
import com.xhpcd.message.*;
import com.xhpcd.protocol.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/13:23
 * @Description:
 */
@Slf4j
public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch Login_Lock = new CountDownLatch(1);
        AtomicBoolean Login = new AtomicBoolean();



        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()

                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)//配置客户端超时连接

               // .option(ChannelOption.SO_BACKLOG,1024)//三次握手四次挥手建立在accept之前，有半连接syns queue队列和全连接 accept queue队列
                .option(ChannelOption.TCP_NODELAY,true)//fasle的话就是开启了nagle算法只有达到一定数量才发送  还有一个ulimit -n Linux操作系统参数用来控制打开的文件数量在高并发场景可以利用
                .group(eventExecutors);
                 bootstrap.channel(NioSocketChannel.class)
                         .handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();

                pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                pipeline.addLast(new MessageCodec());
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new IdleStateHandler(0,10,0));
                pipeline.addLast( new ChannelDuplexHandler(){
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                        IdleStateEvent event =  (IdleStateEvent)evt;
                        if(event.state() == IdleState.WRITER_IDLE){
                            log.info("检测到10秒未发数据发送心跳");
                            ctx.writeAndFlush(new PingMessage());
                        }
                    }
                });
                pipeline.addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        if((msg instanceof LoginResponseMessage)){

                            LoginResponseMessage responseMessage = (LoginResponseMessage)msg;
                            Login.set(responseMessage.getLogin());
                            Login_Lock.countDown();
                        }

                        log.info("客户端收到信息--->{}",msg);
                    }
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        new Thread(()->{
                            Scanner scanner  = new Scanner(System.in);
                            System.out.println("请输入姓名");
                            String username = scanner.next();
                            System.out.println("请输入密码");
                            String pwd = scanner.next();
                            LoginRequestMessage message = new LoginRequestMessage(username, pwd);
                            ctx.writeAndFlush(message);

                            System.out.println("等待后续操作...");
                            try {
                                Login_Lock.await();
                                if(!Login.get()){
                                    ctx.channel().close();
                                    return;
                                }
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
                                    if(!ctx.channel().isWritable()){
                                        //判断
                                        Thread.sleep(500);
                                    }
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
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();

                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        log.info("已断开连接，按任意键继续...");
                        Scanner scanner = new Scanner(System.in);
                        scanner.nextLine();
                    }
                });

            }
        });
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
        ChannelFuture sync = connect.sync();
        Channel channel = sync.channel();
        channel.closeFuture().sync();
        log.info("客户端关闭");
        eventExecutors.shutdownGracefully();
    }
}

/**
 * +---------------------------------------------------------------+
 * | 魔数 5byte | 协议版本号 1byte | 序列化算法 1byte | 指令类型 1byte  |
 * +---------------------------------------------------------------+
 * | 请求序号 4byte |  加密算法 1byte | 填充字段 15byte |  数据长度 4byte |
 * +---------------------------------------------------------------+
 * |                   数据内容 （长度不定）                          |
 * +---------------------------------------------------------------+
 *
 *
 */
