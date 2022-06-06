package com.example.testmodel.config.run;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

public class SpringBootRun2 {
    public static void main(String[] args) {
        /**
         *  系统环境变量，就是配置文件和系统环境的信息 key value格式的
         */
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        for (PropertySource<?> ps : standardEnvironment.getPropertySources()) {
            System.out.println(ps.getProperty("java.vm.version"));

        }
    }
}
