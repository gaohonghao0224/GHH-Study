package springMVC.a20;


import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan
public class WebConfig {
    // 内嵌的 web 容器工厂 ( tomcat 容器)
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
        // 可以根据信息指定 port ，我们一般都会写在配置文件中
        return new TomcatServletWebServerFactory();
    }

    /**
     *      创建 DispatcherServlet  中央控制器
     *

      */
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    /**
     *   DispatcherServlet  最终是要放到 tomcat容器中才能工作，所以此Bean 是为了将 DispatcherServlet 放入到 tomcat容器中
     *   需要两个参数：
     *      参数1： DispatcherServlet
     *      参数2： 由 DispatcherServlet 管控的路径前缀   "/" 代表所有的请求都会被DispatcherServlet管理
     *
     *  知识点：
     *    DispatcherServlet 在项目启动时时不会初始化的，在首次请求时才会初始化
     *
     *    在DispatcherServlet.onRefresh 方法中初始化的
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        // 设置随 taomcat 加载就初始化 ， 值大于1就可以了，多个DispatcherServletRegistrationBean 时表示order
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    /**
     *
     * Handler： 表示具体执行请求的那一段代码
     *
     *  DispatcherServlet 初始化都做了啥
     *      准备文件上传的格式解析器，上传附件的时候表单是不一样的
     *  	initMultipartResolver(context);
     *
     *      国际化解析器
     * 		initLocaleResolver(context);
     *
     * 		initThemeResolver(context);

     *
     * 	    处理路径映射器
     * 	        实现： 会先去容器中寻找 handlerMappings实例，找不到的话会 根据dafaultStrategies方法，
     * 	        去类路径下 DispaterServlet.properties里面去默认实现里面的 ...HandlerMappings的值，里面有初始化所有方法的默认实现的类
     * 	        要注意， 这里面生成的实例，并不会加载到 spring或者web容器中，而是作为 DispaterServlet的成员变量
     *
     * 		initHandlerMappings(context);
     *
     *      适配不同形式的控制器方法，是实际调用控制器方法的
     * 		initHandlerAdapters(context);
     *
     * 	    解析控制器方法异常的解析器
     * 		initHandlerExceptionResolvers(context);
     * 		initRequestToViewNameTranslator(context);
     * 		initViewResolvers(context);
     * 		initFlashMapManager(context);
     */

    // 我们自己在实例中去创建Bean，Dispatcher 就不会走默认的了，这样我们就可以在容器中获取到了
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    // 与上面同理
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        return new RequestMappingHandlerAdapter();
    }
}
