package com.xhpcd;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/01/16:16
 * @Description:
 */
public class ByteBufferString {
    public static void main(String[] args) {

        //此时转换后仍然位于写模式
        ByteBuffer allocate = ByteBuffer.allocate(10);
        ByteBuffer buffer1 = allocate.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer1);
        ByteBufferUtil.debugAll(allocate);
        System.out.println(buffer1==allocate);
        System.out.println(allocate.get(1));
        //wrap 此时包装完毕后位于读模式
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(wrap);
        //Charset 默认切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer2);

    }
}
