package constan_pool;

/**
 *  字符串 初始化流程
 */
public class StringTable_demo2 {
    public static void main(String[] args) {
        // 在执行到这一步时
        // 串池 [a,b]
        // 堆 [ new String("a"), new String("b"), new String("ab")] 最后的 ab 是动态拼接的值
        String s = new String("a") + new String("b");

        // 将 这个字符串对象尝试放入串池，如果没有的话会放入，并将地址返回，如果有就不会放入，将串池已有的地址返回
        // 串池 [a,b,ab]  串池新增 ab
        String s2 = s.intern();
//
        System.out.println(s == "ab");  //  因为没有初始没有ab， 所以上一步放入的ab 就是 s
        System.out.println(s == s2);    // 同理，因为没有，放入的时候会将地址同步返回


//        otherIdea();
    }

    private static void 字面量() {
        // 结果为 true
        String str = "ab";
        System.out.println(str == "a" + "b");
    }


    /**
     *  如果换种思路呢，将 ab 字面量提前声明
     */
    private static void otherIdea() {

        // 串池 [ab,a,b]
        // 堆 [ new String("a"), new String("b"), new String("ab")] 最后的 ab 是动态拼接的值
        String x = "ab";
        String s = new String("a") + new String("b");

        String s2 = s.intern();

        System.out.println(s == "ab");  //  因为已经有ab了， 所以上一步的 ab 不会被放进去    false
        System.out.println(s == s2);    // 串池中已经有了 返回的是x 的地址，所以为 false

    }
}
