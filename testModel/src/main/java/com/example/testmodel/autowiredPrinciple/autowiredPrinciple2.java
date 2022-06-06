package com.example.testmodel.autowiredPrinciple;

public class autowiredPrinciple2 {

/**
 *  累了，而且也不太懂 直接写结论就可以了，到时候想知道的话 去看视频把
 *
 *      数组格式的参数自动注入的方法为：
 *              先去获取数组的类型， 然后再根据此类型 使用 beanNamesForTypeIncludingAncestors方法，去寻找
 *              此方法的作用为 在父子容器中寻找 对用数组类型的所有Bean的名称，然后在根据 beanFactory.getBean 依次返回
 *              然后 数组封装，返回
 *
 *      List格式自动注入的方法为：
 *              先去获取泛型的类型，然后操作一样。但是使用的方法为 resoiveCandidate方法
 *
 *
 *      一个接口多种实现的注入方法：
 *              例子：
 *                  A service。 A1 A2 A3 个实现，在 controller中 属性为 Service service； 此时会如何注入，注入哪一个？
 *
 *                  原理：
 *                      自动注入时，先去获取controller中对应的 依赖描述，发现时Service类型的， 根据 beanNamesForTypeIncludingAncestors方法
 *                      找到所有的Service类型所有的Bean， 默认的选择规则为：
 *                                                              1.  @Qualifier 优先级最高
 *                                                              2.  @Primary 此注解时加载类上的，时BeanDefation的 primary属性，决定某个Bean为主Bean的
 *                                                                  但是只能有一个，多个Primary的时候也会包无法匹配的异常
 *                                                              3.  当前两种都没有时，也会使用 属性名去匹配BeanName
 *
 *
 *      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
 *
 *       BeanFactory.getBean方法无法获取 特殊的Bean，如Application,beanFactory，RourceLoder,
 *       这些属性都在 AbstractApplicationContext 类里的 resolvablDependercies属性里面
 *       但是 DefaultListableBeanFactory 是它的子类，所以 DefaultListableBeanFactory 也可以获取到
 */
}
