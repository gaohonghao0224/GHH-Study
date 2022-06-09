package org.zc.thread.timer;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  ScheduledExecutorService 定时器的使用和了解
 *      线程池创建定时任务 ，可以避免单线程的休眠，异常等问题，不会影响到代码的执行
 *
 */
public class TimerDemo2 {
    public static void main(String[] args) {
        // 创建
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        // 开启定时任务
        pool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 执行输出： AAA  " + LocalTime.now());

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, 2, TimeUnit.SECONDS);

        pool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 执行输出： BBB  " + LocalTime.now());
            }
        }, 5, 2, TimeUnit.SECONDS);

        pool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 执行输出： CCC  " + LocalTime.now());
            }
        }, 5, 2, TimeUnit.SECONDS);

        pool.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 执行输出： DDD  " + LocalTime.now());
            }
        }, 5, 2, TimeUnit.SECONDS);
    }
}
