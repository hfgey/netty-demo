package com.xhpcd;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/02/10:47
 * @Description:
 */
@Slf4j
public class server {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel open = ServerSocketChannel.open();
        
        open.bind(new InetSocketAddress(8080));
        List<SocketChannel> list = new ArrayList<>();
        ByteBuffer allocate = ByteBuffer.allocate(30);
        while (true){
            log.info("connecting.....");
            SocketChannel accept = open.accept();//建立连接  阻塞
            log.info("connected");
            list.add(accept);
            for (SocketChannel channel:list
                 ) {
                log.info("{begin write}");
                channel.read(allocate);//阻塞

                log.info("finish write");
                allocate.flip();
                ByteBufferUtil.debugAll(allocate);
                allocate.clear();
            }

        }
    }
}
