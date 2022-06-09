package org.zc.thread.thred_communication;

/**
 *  取钱的线程类
 *
 */
public class DrawThread extends Thread{
    private Account account;

    public DrawThread(String name, Account account) {
        super(name);
        this.account = account;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(3000);
                account.drawMoney(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
