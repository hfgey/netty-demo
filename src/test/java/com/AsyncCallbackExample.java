package com;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/04/16:53
 * @Description:
 */
import java.util.concurrent.*;

public class AsyncCallbackExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建一个CompletableFuture实例并定义异步操作
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 返回结果
            return 42;
        });

        // 注册回调方法，在操作完成后处理结果
        future.thenAccept(result -> {
            System.out.println("操作完成，结果为: " + result);
            // 在这里可以继续处理结果或执行其他操作
        });

        // 在回调方法执行前，可以继续执行其他任务
        System.out.println("继续执行其他任务...");

        // 阻塞主线程，等待异步操作完成
        future.join();
    }
}
