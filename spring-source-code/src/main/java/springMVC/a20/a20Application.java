package springMVC.a20;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import java.util.Map;

public class a20Application {
    public static void main(String[] args) throws Exception {
        //  web 容器
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        // RequestMappingHandlerMapping 作用为 ： 解析@RequestMapping 以及派生类注解，生成路径和控制器方法的映射关系，在初始化时就会生成，而不是在请求的时候
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        /**
         *  获取映射结果
         *
         *      RequestMappingInfo: 注解上面的信息，请求方式，路径等信息  {PUT [/putPath]}
         *      HandlerMethod: 方法的全限定地址以及签名信息。  springMVC.a20.Controller1#putPath()
         */
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = handlerMapping.getHandlerMethods();
        handlerMethodMap.forEach((k,v)-> {System.out.println(k + "  =  " + v);});

        /**
         *         请求来了，获取控制器方法,
         *         返回 处理器执行链对象, 除了 handler对象还包含拦截器对象
          */
//        // Mock 模仿请求
//        MockHttpServletResponse
//        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/getpath");
//        HandlerExecutionChain executionChain = handlerMapping.getHandler(request);
//        System.out.println(executionChain);

//        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
//        handlerAdapter.invokeHandlerMethod(request)
    }
}
