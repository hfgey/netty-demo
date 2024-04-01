package com.xhpcd;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/02/13:09
 * @Description:
 */
@Slf4j
public class server2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(8080));
        open.configureBlocking(false);
        List<SocketChannel> list = new ArrayList<>();
        ByteBuffer allocate = ByteBuffer.allocate(30);
        while (true){
            log.info("connecting.....");
            SocketChannel accept = open.accept();//建立连接  阻塞(之前设置了为非阻塞)
            accept.configureBlocking(false);
            log.info("connected");
            list.add(accept);
            for (SocketChannel channel:list
            ) {
                log.info("{begin write}");
                channel.read(allocate);//阻塞 (之前设置了为非阻塞)

                log.info("finish write");
                allocate.flip();
                ByteBufferUtil.debugAll(allocate);
                allocate.clear();
            }

        }
    }
}
