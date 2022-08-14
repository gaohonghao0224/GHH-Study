package org.zc.aliJSON;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        User sysDep = new User();
        sysDep.setName("11");

        List<Object> list = new ArrayList<>();
        list.add(sysDep);
//        list.add(sysDep);
        List<Object> list2 = new ArrayList<>();
        for (Object o : list) {
            list2.add(o);
        }

        String s = JSON.toJSONString(list2);
        System.out.println(s);
    }
}
