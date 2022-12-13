package GC;

import java.util.ArrayList;
import java.util.List;

/**
 *  演示 GC ROOTS
 *
 */
public class GC_ROOTS_demo {
    public static void main(String[] args) throws Exception {
        List<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(" step 1  -> init list");
        System.in.read();

        list = null;
        System.out.println(" step 2 - list set null");
        System.in.read();
        System.out.println(" end ...");
    }
}
