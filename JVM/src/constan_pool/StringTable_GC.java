package constan_pool;

/**
 * 演示  StringTable  垃圾回收
 * -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
 */
public class StringTable_GC {
    public static void main(String[] args) throws Exception {

        int i = 0;
        try {
            for (int j = 0; j < 100000; j++) {
                // 插入串池
                String.valueOf(i).intern();
                i++;
            }
        }catch (Throwable e){

        }finally {
            System.out.println(i);
        }
    }
}
