package org.zc.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        list.stream().distinct().forEach(System.out::println);
//        System.out.println(list.toString());

//        Stream<String> s1 = list.stream().filter(s -> s.startsWith("马"));
//        Stream<String> s2 = Stream.of("java1","java2");
//        s1.map( s ->  "亲爱的" + s ).forEach(System.out::println);

//        Stream<String> s3 = Stream.concat(s1,s2);

//        s3.forEach(System.out::println);

    }
}
