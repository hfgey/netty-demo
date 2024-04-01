package com.xhpcd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/01/20:54
 * @Description:
 */
public class transferToFrom {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(8080));
        SocketChannel accept = open.accept();

        try (  FileInputStream inputStream = new FileInputStream("from.txt");
               FileOutputStream outputStream = new FileOutputStream("to.txt");
               FileChannel channel = inputStream.getChannel();
              FileChannel channel1 = outputStream.getChannel()){

            channel.transferTo(0,channel.size(),channel1);

        }catch (IOException e){
               e.printStackTrace();
        }

    }
}
