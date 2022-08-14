package org.zc.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    Employee e1 = new Employee("a",10);
    Employee e2 = new Employee("b",10);
    Employee e3 = new Employee("c",20);
    Employee e4 = new Employee("a",10);

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

        // distin
    }

    /**
     *  测试 去重
     *      根据元素的 hashCode和equals 方法去重的，如果想去重 对象，需要重写其指定方法
     *       lombok的 @Data 注解会重写其方法
     */
    @Test
    void distinct(){
        List<Employee> list = new ArrayList<>();
        Collections.addAll(list,e1,e2,e3,e4);

        Stream<Employee> stream = list.stream().distinct();
        List<Employee> collect = stream.collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    // limit  获取流中的前N个元素

    // skip  扔掉流中的前N个元素

    @Test
    void Tmap(){
        List<Employee> list = new ArrayList<>();
        Collections.addAll(list,e1,e2,e3,e4);
        System.out.println(list);

        System.out.println(" ---------------   ---------------   ---------------   ---------------  ");
        List<Employee> collect = list.stream().map(item ->
                {
            item.setSalary(item.getSalary() + 100.0);
            return item;
        }
        ).collect(Collectors.toList());

        System.out.println(collect);
    }

    /**
     *  可以直接返回，感觉是本身的整体就是 item 所以可以直接返回
     *  上面的是对象，只是修改了某个属性，无法返回整个对象
     */
    @Test
    void Tstr(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");

        List<String> collect = list.stream().map(item -> item.concat("a")).collect(Collectors.toList());

        System.out.println(collect);
    }

    //  --------------------------------        终结方法        ------------------------------------


    // 集合中是否有一个元素满足条件
    @Test
    void anyMatch(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        boolean b = list.stream().anyMatch(s -> s.contains("高1"));

        System.out.println(b);

    }


    // 集合中是否都满足条件
    @Test
    void allMatch(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        boolean b = list.stream().allMatch(s -> s.length() > 2);

        System.out.println(b);
    }

    // 集合中是否所有元素都不满足条件
    @Test
    void noneMatch(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        boolean b = list.stream().noneMatch(s -> s.length() > 2);

        System.out.println(b);
    }


    /**
     *  返回集合中任意元素
     */
    @Test
    void findAny(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        // 并行会改变，串行默认都是第一个
        Optional<String> any = list.parallelStream().findAny();
        System.out.println(any.get());
    }

    /**
     *  返回集合中第一个元素
     */
    @Test
    void findFirst(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"马颖欣","马海军","马军","高洪浩","高洪浩");
        // 并行会改变，串行默认都是第一个
        Optional<String> any = list.parallelStream().findFirst();
        System.out.println(any.get());
    }

    /**
     *  将流中的所有元素 反复集合起来得到一个结果
     */
    @Test
    void reduce(){
        List<Employee> list = new ArrayList<>();
        Collections.addAll(list,e1,e2,e3,e4);
        list.stream().reduce(100d,(acc, item) -> {
            acc += item.getSalary();
//            Employee employee = new Employee();
//            employee.setSalary(acc.getSalary());
            return acc;
        }, (acc, item) -> null);

//        System.out.println(aDouble);

    }

    void reduce2(){
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list,1,2,3,4);
        list.stream().reduce((acc,item) -> {
                acc += item;
            return acc; }
        );

    }

}
