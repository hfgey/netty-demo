package com.xhpcd;

import ch.qos.logback.classic.net.SimpleSocketServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/03/14:55
 * @Description:
 */
@Slf4j
public class boss {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        Thread.currentThread().setName("boss");
        open.bind(new InetSocketAddress(8080));
        open.configureBlocking(false);
        Selector selector = Selector.open();
        open.register(selector,SelectionKey.OP_ACCEPT);
        work w = new work("work-0");

        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey ssc = iterator.next();
                iterator.remove();
                ServerSocketChannel channel = (ServerSocketChannel) ssc.channel();
                SocketChannel accept = channel.accept();
                accept.configureBlocking(false);
                log.info("建立连接{}",accept);
                w.register();
                SelectionKey register = accept.register(w.selector, 0, null);
                register.interestOps(SelectionKey.OP_READ);
            }
        }
    }
    static class work implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean is = false;
        public work(String name){
            this.name = name;
        }
        public void register() throws IOException {
            if(!is){
                thread = new Thread(this,name);
                selector = Selector.open();
                is = true;
                thread.start();
            }
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true){
                selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(next.isReadable()){
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                        channel.read(byteBuffer);
                        byteBuffer.flip();
                        log.info("打印");
                        ByteBufferUtil.debugAll(byteBuffer);
                    }
                }
            }
        }
    }
}
