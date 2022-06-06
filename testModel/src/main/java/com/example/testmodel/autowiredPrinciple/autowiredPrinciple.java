package com.example.testmodel.autowiredPrinciple;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Configuration
public class autowiredPrinciple {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(autowiredPrinciple.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        // 1. 根据成员变量的类型注入
        // DependencyDescriptor 是根据类的那个元数据去查找， 根据属性的数据数据类型，去容器里面去根据类型去寻找，
        // 如 bean3直接使用  doResolveDependency 是根据 Optional去找， 根本找不到Optional类型的，由此可见是获取元数据属性的类型去找的
        DependencyDescriptor dd1 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
        System.out.println(beanFactory.doResolveDependency(dd1, "bean1", null, null));

        // 2. 根据参数的类型注入
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
        System.out.println("第二种  " + dd2.getDependencyType());
        System.out.println(beanFactory.doResolveDependency(dd2, "bean1", null, null));

        // 3. 结果包装为 Optional<Bean2>
        // 获取到的 依赖描述的类型 为 Optional 类型，我们要寻找它里面嵌套的Bean2类型
        DependencyDescriptor dd3 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"),false);
        System.out.println("第三种  " + dd3.getDependencyType());
        if(dd3.getDependencyType() == Optional.class){
            // 先找Bean2，在包装成Optional<Bean2>
            // 增加此描述符的嵌套级别
            dd3.increaseNestingLevel();
            System.out.println("第三种 real  " + dd3.getDependencyType());
            Bean2 bean = (Bean2) beanFactory.doResolveDependency(dd1, "bean1", null, null);
            // 包装 Optional<Bean2>
            Optional<Bean2> result = Optional.ofNullable(bean);
            System.out.println(result);
        }

        // 4. 结果包装为 ObjectProvider
        // 第四种原理和第三种同理，都属于嵌套
        DependencyDescriptor dd4 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"),false);
        System.out.println("第四种  " + dd4.getDependencyType());
        if(ObjectFactory.class == dd4.getDependencyType()){
            dd4.increaseNestingLevel();
            System.out.println("第四种  real " + dd4.getDependencyType());
            /**
             *  ObjectFactory的知识：
             *      这个是一个 ObjectFactory, 只有调用 getObject的时候，才回去加载Bean， 所以说 ObjectFactory是一个延迟加载的类型，这也是之前 不同Scope的Bean可以使用 ObjectFactory装配的原因
             *
             *      同样，延迟加载也是和上一个例子的区别，不是两个完全相同的东西
             */
            ObjectFactory objectFactory = new ObjectFactory() {
                @Override
                public Object getObject() throws BeansException {
                    return beanFactory.doResolveDependency(dd4,"bean1",null,null);
                }
            };

            System.out.println(objectFactory.getObject());
        }

        // 5. 对@Lazy的处理
        /**
         *  @Lazy 注解的原理为
         *      会对目标属性生成一个目标属性对应的代理类，当我们调用目标类的方法时，
         *      才会真实获取真实的 target对象
         */
        DependencyDescriptor dd5 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);
        // 此方法会检查 对用的依赖描述上 是否含有 @Lazy注解，有的话返回代理对象，没有的话返回目标对象
        Object proxy = resolver.getLazyResolutionProxyIfNecessary(dd5,"bean1");
        System.out.println(proxy);
        System.out.println(proxy.getClass());



    }

    static class Bean1{
        @Autowired @Lazy
        private Bean2 bean2;

        @Autowired
        public void setBean2( Bean2 bean2){
            this.bean2 = bean2;
        }

        @Autowired
        private Optional<Bean2> bean3;

        @Autowired
        private ObjectFactory<Bean2> bean4;

    }

    @Component("bean2")
    static class Bean2{

    }


}
