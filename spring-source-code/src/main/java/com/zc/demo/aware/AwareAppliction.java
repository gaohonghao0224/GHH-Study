package com.zc.demo.aware;

import org.springframework.context.support.GenericApplicationContext;

public class AwareAppliction {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myBean",MyBean.class);

        context.refresh();
        context.close();
    }
}
