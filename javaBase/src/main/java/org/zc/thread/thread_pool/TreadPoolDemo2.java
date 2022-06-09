package org.zc.thread.thread_pool;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9 
 * @since 1.0
 */

import java.util.concurrent.*;

/**
 *   创建固定线程
 */
public class TreadPoolDemo2 {
    public static void main(String[] args) {
        // 创建固定数量的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.execute(new MyRunable());
        pool.execute(new MyRunable());
        pool.execute(new MyRunable());
        pool.execute(new MyRunable()); // 不会执行这个任务
    }
}
