package org.zc.thread.thread_safe;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 账户类
 */
@Data
public class Account {
    private String cardId;
    private double money;
    private Lock lock = new ReentrantLock();

    public Account() {
    }

    public Account(String s, double money) {
        this.money = money;
        this.cardId = s;
    }

    public void drawMoney(double expend) {
        String currentUser = Thread.currentThread().getName();
        try {
            lock.lock();
            if (expend <= money) {
                System.out.println(currentUser + " 取了 " + expend + " 块");
                this.money -= expend;
                System.out.println(currentUser + " 取钱之后剩余 " + money + " 块");
            } else {
                System.out.println(currentUser + " 取钱的时候没钱了");
            }
        } finally {
            lock.unlock();
        }
    }
}
