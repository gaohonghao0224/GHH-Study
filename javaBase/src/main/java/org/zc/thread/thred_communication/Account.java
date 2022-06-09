package org.zc.thread.thred_communication;

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

    public synchronized void depositMoney(double saveMoney){
        String currentUser = Thread.currentThread().getName();
        try {
            if (this.money == 0) {
                System.out.println(currentUser + " 存了 " + saveMoney + " 块");
                this.money += saveMoney;
                this.notifyAll();
                this.wait();
            } else {
                this.notifyAll();
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  synchronized void drawMoney(double expend) {
        String currentUser = Thread.currentThread().getName();
        try {
            if (this.money > 0) {
                System.out.println(currentUser + " 取了 " + expend + " 块");
                this.money -= expend;
                this.notifyAll();
                this.wait();
            } else {
                this.notifyAll();
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
