package org.zc.thread.craete;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  线程的 一二种 创建方式：
 *      extends Thread
 *      implements  Runable 接口，
 */
public class ThreadDemo1 {
    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(" 子线程   " + i);
            }
        }).start();

        //new Thread(new Runnable(){
        //    @Override
        //    public void run() {
        //        for (int i = 0; i < 5; i++) {
        //            System.out.println(" 子线程   " + i);
        //        }
        //    }
        //}).start();


        for (int i = 0; i < 5; i++) {
            System.out.println(" 主线程   " + i);
        }
    }
}
