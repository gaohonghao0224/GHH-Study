package com.example.testmodel.config.run;

import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Arrays;

@SuppressWarnings("all")
public class springBootRun1 {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication();
        application.addInitializers(applicationContext -> System.out.println(" 初始化增强1"),applicationContext -> System.out.println(" 初始化增强2"));

        System.out.println(" >>>>>>>>>>>>>>>>>  1. 创建事件发布器");
        /**
         * 源代码对应：
         *      SpringApplicationRunListeners listeners = getRunListeners(args);
         *          从 spring.factories 里面去读取 事件发布器对应的实现类，
         *          再次强调，此类事 发布器，不是监听器
         *
         *      listeners.starting(bootstrapContext, this.mainApplicationClass);
         *          然后就马上调用了 starting 事件，表示spring boot 开始启动
         *          详细看 EventPublish 第十五行
         *
         *
         */


        System.out.println(" >>>>>>>>>>>>>>>>>  2. 封装启动 args");
        /**
         * 源代码对应：
         *      run方法
         *      			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
         *      		    封装	args， 一般封装选项信息，也就是 --开头的命令行参数
         */
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

        System.out.println(" >>>>>>>>>>>>>>>>>  3. 创建 environment 环境变量 并配置信息");
        /**
         * 源代码对应：
         *    prepareEnvironment 方法:
         *       ConfigurableEnvironment environment = getOrCreateEnvironment();
         *       创建获取 environment 环境变量
         *
         * 		  configureEnvironment(environment, applicationArguments.getSourceArgs());
         *        将第二步中的参数信息封装在 environment 中， 此步的作用就是为了将命令行参数加入到 环境中
         *
         */

        System.out.println(" >>>>>>>>>>>>>>>>>  4. 统一格式化 evironment里面的key ");
        /**
         * 源代码对应：
         *    prepareEnvironment 方法:
         *       ConfigurationPropertySources.attach(environment);
         *       统一格式化 evironment里面的key ，
         *
         *        例如有的键是 驼峰命名，有的是横杠区分
         *        因为它要获取不同文件中， 如 properties文件，命令行，系统环境变量等不同地方的信息
         *        肯定命名会有不同，在这里统一格式化
         *
         *
         */

        System.out.println(" >>>>>>>>>>>>>>>>>  5. 发布 environment 发布器，由监听器接收，并添加其他数据源  ");
        /**
         * 源代码对应：
         *    prepareEnvironment 方法:
         *        listeners.environmentPrepared(bootstrapContext, environment);
         *        发布 environment 发布器 , 参考 EventPublish 第46行
         *        由 对应的监听器接收，并且执行 spring.factories 里面对应监听器的后处理器方法
         *        添加更多的 “源”
         *
         *       典型的有：
         *                application.properties 里面的数据 （最重要）
         *                随机源，可以获取随机数等
         *
         *
         */

        System.out.println(" >>>>>>>>>>>>>>>>>  6. 绑定 SpringApplication 在 environment 里面的对应数据  ");
        /**
         * 源代码对应：
         *    prepareEnvironment 方法:
         *        bindToSpringApplication(environment);
         *       绑定 SpringApplication 在 environment 里面的对应数据
         *
         *        典型的有：
         *              banner图 的类型
         *
         *
         *
         */

        System.out.println(" >>>>>>>>>>>>>>>>>  7. 输出banner图  ");
        /**
         * 源代码对应：
         *    run 方法:
         *      		Banner printedBanner = printBanner(environment);
         *      	    输出banner图
         *
         *
         *
         */
        System.out.println(" >>>>>>>>>>>>>>>>>  8. 创建 spring 容器");
        /**
         * 源代码对应：
         *    run 方法:
         *      		context = createApplicationContext();
         *      	    根据对应的jar包，获取到对应的容器类型
         *
         *
         *
         */
        GenericApplicationContext context = new GenericApplicationContext();

        System.out.println(" >>>>>>>>>>>>>>>>>  9. 准备容器，执行初始化扩展");
        /**
         * 源代码对应：
         *    prepareContext 方法:
         *      		applyInitializers(context);
         *      	    执行容器的初始化扩展方法
         *
         *              listeners.contextPrepared(context);
         *              spring容器创建好会发布此事件
         *
         */

        for(ApplicationContextInitializer initializer : application.getInitializers()){
            // 会调用 11行添加的 初始化增强
            initializer.initialize(context);
        }

        System.out.println(" >>>>>>>>>>>>>>>>>  10. 加载Bean定义, 准备BeanDefinition");
        /**
         * 源代码对应：
         *    prepareContext 方法:
         *      		Set<Object> sources = getAllSources();
         *      	    获取到所有的 Bean 加载的源，如 配置类，引导类，以及配置文件等可以创建Bean的地方
         *
         *              load(context, sources.toArray(new Object[0]));
         *              将源里面的bean 加载到 applicationContext中
         *
         *              listeners.contextLoaded(context);
         *              所有BeanDefinition 加载完毕会执行此事件
         *
         */

        //猜测： 读取能够读取注解修饰的Bean定义，从 beanFactory 中，应该是要获取 beanFactory里面的后处理器功能
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(context.getDefaultListableBeanFactory());
        context.registerBean(runConfig.class);

        System.out.println(" >>>>>>>>>>>>>>>>>  11. refresh 容器");
        /**
         * 源代码对应：
         *     refreshContext 方法  --> refresh 方法
         *              applicationContext.refresh();
         *              初始化容器
         *
         *              listeners.started(context, timeTakenToStartup);
         *              在容器初始化之后，就发布 springboot 启动完毕事件
         *
         */
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name + " --  " + context.getBeanFactory().getBeanDefinition(name).getResourceDescription());
        }

        System.out.println(" >>>>>>>>>>>>>>>>>  12. 执行 runner");
        /**
         *   源代码对应：
         *
         *          callRunners(context, applicationArguments);
         *          回调  CommandLineRunner 或者 ApplicationRunner 方法
         *
         *          listeners.ready(context, timeTakenToReady);
         *          springboot 程序准备启动完成
         *
         *
         */
        // 两种runner的作用为 在springboot的运行的最后阶段被回调，可以使用此方法来预加载一些数据
        for(CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()){
            runner.run(args);
        }

        for(ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()){
            runner.run(applicationArguments);
        }


        context.close();

    }


    static class Bean1 {};
    static class Bean2 {};
    static class Bean3 {};
    static class Bean4 {};
    static class Bean5 {};


    @Configuration
    static class runConfig{
        @Bean
        public Bean5 bean1(){
            return new Bean5();
        }

        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public CommandLineRunner commandLineRunner(){
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("commandLineRunner () ..   " + Arrays.toString(args));
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner(){
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("  applicationRunner() ...  " + Arrays.toString(args.getSourceArgs()));
                }
            };
        }
    }


}
