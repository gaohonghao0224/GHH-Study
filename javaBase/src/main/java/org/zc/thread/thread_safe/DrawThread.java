package org.zc.thread.thread_safe;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  取钱的线程类
 *
 */
public class DrawThread extends Thread{
    private Account account;

    public DrawThread(String name,Account account) {
        super(name);
        this.account = account;
    }

    @Override
    public void run() {
        account.drawMoney(100000);
    }
}
