package com.xhpcd;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/02/16:09
 * @Description:
 */

public class selector {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel open = ServerSocketChannel.open();

        Selector selector1 = Selector.open();
        open.bind(new InetSocketAddress(8080));
        open.configureBlocking(false);
        SelectionKey sscKey = open.register(selector1, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        while (true){
            System.out.println("ooo");
            selector1.select();

            Set<SelectionKey> selectionKeys = selector1.selectedKeys();
            //由于存在删除等操作所以使用iterator遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){

                System.out.println("sss");
                SelectionKey next = iterator.next();
                iterator.remove();
                if(next.isAcceptable()){
                    ByteBuffer allocate = ByteBuffer.allocate(5);
                    ServerSocketChannel channel = (ServerSocketChannel)next.channel();

                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    SelectionKey acKey = accept.register(selector1, 0, allocate);
                    acKey.interestOps(SelectionKey.OP_READ);
                }else if(next.isReadable()){
                    ByteBuffer allocate = (ByteBuffer) next.attachment();
                    SocketChannel channel2 = (SocketChannel) next.channel();
                    try{
                        int read = channel2.read(allocate);
                        if(read == -1){
                            next.cancel();
                        }else {
                            t2.split(allocate);
                            if(allocate.position()==allocate.limit()){
                                ByteBuffer newByteBuffer = ByteBuffer.allocate((allocate.capacity()*2));
                                allocate.flip();
                                newByteBuffer.put(allocate);
                                next.attach(newByteBuffer);
                            }
                        }
                    }catch (IOException e){
                        System.out.println("ppp");
                        next.cancel();
                    }

                }
            }
        }

    }
}
