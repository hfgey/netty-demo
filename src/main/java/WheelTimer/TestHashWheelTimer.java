package WheelTimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2024/03/29/20:44
 * @Description:
 */
public class TestHashWheelTimer {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            System.out.println("kk");
        },1,1,TimeUnit.SECONDS);

      /*  ThreadGroup ll = new ThreadGroup("ll");
        System.out.println(ll.getParent().getName());*/
    }
    public static void main1(String[] args) {
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(1, TimeUnit.SECONDS,20);
        Timeout timeout = hashedWheelTimer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("执行" + System.currentTimeMillis());
            }
        }, 2, TimeUnit.SECONDS);
        System.out.println("当前时间"+System.currentTimeMillis());
    }
}
