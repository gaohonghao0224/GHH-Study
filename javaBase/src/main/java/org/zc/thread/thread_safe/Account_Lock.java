package org.zc.thread.thread_safe;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

/**
 *  Lock 锁
 *      Lock 本身就是一个锁对象， 不需要在寻求 synchronized 这种锁参数对象了
 *      Lock 本身就已经实现了 synchronized的功能，相对于前者来说更便携和灵活
 */

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 账户类
 */
@Data
public class Account_Lock {
    private String cardId;
    private double money;
    /**
     *  锁对象建议 用 final修饰
     *  初始化值，便于每个实例都会有一个锁对象，互相不冲突
     */
    private final Lock lock = new ReentrantLock();

    public Account_Lock() {
    }

    public Account_Lock(String s, double money) {
        this.money = money;
        this.cardId = s;
    }

    public  void drawMoney(double expend) {
        String currentUser = Thread.currentThread().getName();
            if (expend <= money) {
                System.out.println(currentUser + " 取了 " + expend  + " 块");
                this.money  -= expend;
                System.out.println(currentUser + " 取钱之后剩余 " + money  + " 块");
            }else{
                System.out.println(currentUser + " 取钱的时候没钱了");
        }
    }
}
