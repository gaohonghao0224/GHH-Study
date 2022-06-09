package org.zc.thread.thread_safe;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  线程安全
 */
public class ThreadDemo {
    public static void main(String[] args) {
        // 定义线程类，创建一个共享的账户对象
        Account account = new Account("ICBC-1111",100000);

        // t1 线程
        new DrawThread("小明",account).start();
        // t2 线程
        new DrawThread("小红",account).start();

    }
}
