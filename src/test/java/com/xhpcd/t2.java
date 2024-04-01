package com.xhpcd;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/01/17:06
 * @Description:处理黏包和半包
 * 黏包主要是发送端出于性能考虑，将多条独立数据发送
 * 半包可能是服务器端缓存大小有限导致读取发生截断
 */
public class t2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(30);

        buffer.put("hello world \nI am ".getBytes());
        split(buffer);

        buffer.put("HSJ \n to get it \n".getBytes());
        split(buffer);
    }
    public static void split(ByteBuffer buffer){
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if(buffer.get(i)=='\n'){
                int length = i- buffer.position()+1;
                ByteBuffer allocate = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    allocate.put(buffer.get());
                }
                ByteBufferUtil.debugAll(allocate);
            }
        }
        buffer.compact();
    }
}
