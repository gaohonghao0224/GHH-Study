package org.zc.thread.thred_communication;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  线程通信
 *      线程通信就是 不同的线程间，依靠共享的数据源状态进行通信，也就是状态变化
 */
public class ThreadDemo1 {
    public static void main(String[] args) {
        Account accoun = new Account("ICBC-112",0);

        new DrawThread("小明",accoun).start();
        new DrawThread("小红",accoun).start();

        new DepositThread("亲爹", accoun).start();
        new DepositThread("干爹", accoun).start();
        new DepositThread("岳父", accoun).start();
    }
}
