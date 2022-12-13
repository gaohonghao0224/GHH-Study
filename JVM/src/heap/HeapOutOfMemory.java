package heap;

/**
 *  演示堆内存溢出
 *  -Xmx8m
 */
public class HeapOutOfMemory {
    public static void main(String[] args) throws Exception {
        System.out.println(" 程序开始...");
        Thread.sleep(30000);
        byte[] array = new byte[1024 * 1024 * 2];  // 10Mb
        System.out.println(" 创建了一个10Mb大小的数组...");
        Thread.sleep(30000);
        array = null;
        System.gc();
        System.out.println(" 显示调用 GC...");
        Thread.sleep(100000L);

    }
}