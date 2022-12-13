package meta;

/**
 *  演示 元空间 （方法区） 内存溢出
 *  -XX:MaxMetaspaceSize=8m
 */
public class MetaSpaceOutOfMemory extends ClassLoader{
    public static void main(String[] args) {
        int j = 0;
        try {
            MetaSpaceOutOfMemory outOfMemory = new MetaSpaceOutOfMemory();
            for (int i = 0; i < 10000; i++,j++) {
//                ClassWr
            }
        }catch (Exception e){

        }
    }
}
