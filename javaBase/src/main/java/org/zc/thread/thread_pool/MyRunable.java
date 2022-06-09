package org.zc.thread.thread_pool;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */
public class MyRunable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  输出了   " +  i);
        }

        // 休眠线程，便于测试
        System.out.println(Thread.currentThread().getName() + "开始休眠");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
