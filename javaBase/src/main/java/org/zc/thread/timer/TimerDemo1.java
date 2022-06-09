package org.zc.thread.timer;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */

import java.util.Timer;
import java.util.TimerTask;

/**
 *  Timer 定时器的使用和了解
 */
public class TimerDemo1 {
    public static void main(String[] args) {
        // 1. 创建Timer定时器
        Timer timer = new Timer();
        // 2. 调用方法，处理定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 执行一次  ");
            }
        }, 10000, 2000);
    }
}
