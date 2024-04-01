package com;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.util.concurrent.ThreadPerTaskExecutor;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2024/02/26/18:51
 * @Description:
 */
public class PerExecutor {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        ThreadFactory build = threadFactoryBuilder.build();
        ThreadPerTaskExecutor threadPerTaskExecutor = new ThreadPerTaskExecutor(build);
        threadPerTaskExecutor.execute(()->{
            System.out.println("1");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("11");
        });
        threadPerTaskExecutor.execute(()->{
            System.out.println("2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("22");
        });
    }
}
