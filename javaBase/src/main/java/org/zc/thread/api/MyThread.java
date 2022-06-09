package org.zc.thread.api;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  自定义 子线程
 */
public class MyThread  extends  Thread{

    public MyThread() {
        super();
    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            /**
             *  Thread.currentThread()
             *      获取当前运行的线程
             */
            System.out.println(Thread.currentThread().getName() + " 线程  " + i );
        }
    }
}
