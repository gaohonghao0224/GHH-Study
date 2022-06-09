package org.zc.thread.thred_communication;

/**
 *  存钱的线程类
 *
 */
public class DepositThread extends Thread{
    private Account account;

    public DepositThread(String name, Account account) {
        super(name);
        this.account = account;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(3000);
                account.depositMoney(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }    }
}
