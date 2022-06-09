package org.zc.thread.craete;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/8
 * @since 1.0
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  Callable接口实现 线程功能    jdk1.5 有的功能
 *
 *     此种方式的优势为，可以获取到线程执行的结果， 其余两种方式都无法获取到线程执行的结果
 */
public class ThreadCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new MyCallable(100);
        // 适配器思想， 适配 Runable接口类型
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        // get方法会等待 线程执行完毕才会去获取结果
        String result = futureTask.get();
        System.out.println(" Callable的结果为" + result);
    }
}

/**
 *  实现 Callable接口，填写泛型，泛型就是返回值的类型
 */
class MyCallable implements Callable<String>{

    private int n;

    public MyCallable(int n) {
        this.n = n;
    }

    /**
     * 重写callable方法
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return "子线程执行的结果为   " + sum;
    }
}
