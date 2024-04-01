package com.xhpcd;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/01/15:50
 * @Description:
 */
public class t1 {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(15);

        allocate.put((byte) 0x61);
        allocate.put(new byte[]{0x61,0x62,0x63});
        ByteBufferUtil.debugAll(allocate);
        allocate.flip();
        byte[] b = new byte[3];
        ByteBuffer byteBuffer = allocate.get(b);
        System.out.println(Arrays.toString(b));

        ByteBufferUtil.debugAll(allocate);
        allocate.rewind();
        ByteBufferUtil.debugAll(allocate);
    }
}
