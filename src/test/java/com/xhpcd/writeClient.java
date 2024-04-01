package com.xhpcd;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/03/10:53
 * @Description:
 */
public class writeClient {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("127.0.0.1",8080));

        int count = 0;
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(3);
            count+= open.read(buffer);
            System.out.println(count);
        }
    }
}
