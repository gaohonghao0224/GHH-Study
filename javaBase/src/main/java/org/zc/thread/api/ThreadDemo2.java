package org.zc.thread.api;

/**
 *  sleep  线程休眠
 */

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */
public class ThreadDemo2 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println("main 线程   " +  i);
            if (i==3) {
                Thread.sleep(3000);
            }
        }

    }
}
