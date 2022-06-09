package org.zc.thread.api;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */
public class ThreadDemo1 {
    public static void main(String[] args) {
        new MyThread("11号").start();

        new MyThread("22号").start();

        for (int i = 0; i < 5; i++) {
            System.out.println("main 线程   " +  i);
        }

    }
}
