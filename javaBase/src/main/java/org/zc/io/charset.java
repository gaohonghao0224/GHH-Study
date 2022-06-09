package org.zc.io;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *  字符集
 */
public class charset {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 编码，把文字转换成字节
        String name = "abc我爱你中国";
        byte[] bytes = name.getBytes("GBK"); // 不写参数的话 默认当前代码默认字符集编码  utf-8
        System.out.println(bytes.length);
        System.out.println(Arrays.toString(bytes));


        // 2. 解码： 把字节转换成对应的中文形式 （编码前和编码后的字符集必须一致，否则乱码）
        String rs = new String(bytes,"GBK");
        System.out.println  (rs);

    }
}
