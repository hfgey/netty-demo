package com.xhpcd;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/03/10:30
 * @Description:
 */
public class writeServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.configureBlocking(false);
        open.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        open.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                if(next.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = channel.accept();
                    String s = "你好世界hello你好世界hello你好世界hello";
                    ByteBuffer encode = Charset.defaultCharset().encode(s);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,0,encode);

                    int write = socketChannel.write(encode);
                    System.out.println(write);
                    if(encode.hasRemaining()){
                        next.interestOps(next.interestOps()+SelectionKey.OP_WRITE);

                    }
                }else if (next.isWritable()){
                    ByteBuffer attachment = (ByteBuffer) next.attachment();
                    SocketChannel channel = (SocketChannel) next.channel();
                    channel.write(attachment);
                    if(!attachment.hasRemaining()){
                        next.attach(null);
                        next.interestOps(next.interestOps()-SelectionKey.OP_WRITE);
                    }
                }

            }
        }
    }
}
