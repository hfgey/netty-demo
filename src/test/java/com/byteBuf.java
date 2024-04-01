package com;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.NEWLINE;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/05/12:49
 * @Description:
 */
public class byteBuf {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();
        System.out.println(byteBuf);
        System.out.println(buffer);
        buffer.writeBytes(new byte[]{'a','b','c','d'});
        ByteBuf slice1 = buffer.slice(0, 2);
        ByteBuf slice2 = buffer.slice(2, 2);
        log(slice2);
        slice1.release();
        log(buffer);
     //   log(slice1);

    }
    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }


}
