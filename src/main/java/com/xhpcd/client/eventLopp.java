package com.xhpcd.client;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/13/14:09
 * @Description:
 */
public class eventLopp {
    public static void main(String[] args) {
        EventLoop next = new NioEventLoopGroup().next();
        next.execute(()->{
            System.out.println("nihao");
        });
    }
}
