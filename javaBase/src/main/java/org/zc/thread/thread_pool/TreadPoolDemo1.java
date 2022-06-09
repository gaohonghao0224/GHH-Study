package org.zc.thread.thread_pool;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */

import java.util.concurrent.*;

/**
 * 测试线程池
 */
public class TreadPoolDemo1 {
    public static void main(String[] args) {
        /**
         *  创建线程池参数：
         *      核心线程数量，  线程池最大数量,  临时线程保存时间， 时间单位， 任务列表， 创建线程的工厂， 拒绝策略
         */
        ExecutorService pool = new ThreadPoolExecutor(3, 5, 6,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 模拟任务
        Runnable runnable = new MyRunable();
        // 核心线程可以处理的数量   corePoolSize = 3
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);

        // 任务列表的数量      new ArrayBlockingQueue<>(5)
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);

        // 开始创建临时线程   -- 在上面两个条件都满足之后， 才可以开始创建临时线程， 临时线程数量 =  maxmumpoolSize - corePoolSize
        pool.execute(runnable);
        pool.execute(runnable);

        // 不创建，拒绝策略触发  -- 拒绝策略没有生效啊，没有抛异常，是JDK的问题吧
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);



    }
}
