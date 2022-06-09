package org.zc.io;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * @author GaoHH
 * @email gaohonghao0224@163.com
 * @date 2022/6/9
 * @since 1.0
 */
public class FileInputStreamDemo1 {
    public static void main(String[] args) throws Exception{
        InputStream is = new FileInputStream("src\\data.txt");

        // 读取一个字符
        int b1 = is.read();
        System.out.println((char) b1);

    }
}
