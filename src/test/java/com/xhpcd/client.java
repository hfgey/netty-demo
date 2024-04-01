package com.xhpcd;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/02/10:37
 * @Description:
 */
public class client {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();

        boolean connect = open.connect(new InetSocketAddress("127.0.0.1",8080));
        open.write(Charset.defaultCharset().encode("你好"));
        open.close();
    }
}
