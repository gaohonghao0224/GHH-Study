package springMVC.a20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Controller1 {


    @Autowired
    HttpServletRequest httpServletRequest;

    HandlerMethod handlerMethod;

    @GetMapping("/getPath")
    public ModelAndView getPath(HttpServletRequest request,HttpServletResponse response) throws Exception {
        this.httpServletRequest = request;
        System.out.println("  ---------   getPath  -------------  ");

//        /**
//         *         请求来了，获取控制器方法,
//         *         返回 处理器执行链对象, 除了 handler对象还包含拦截器对象
//         *
//         *         HandlerExecutionChain with [springMVC.a20.Controller1#getPath(HttpServletRequest)] and 0 interceptors
//         */
//        HandlerExecutionChain executionChain = handlerMapping.getHandler(request);
//        this.handlerMethod = ((HandlerMethod) executionChain.getHandler());
//        handlerAdapter.invokeHandlerMethod(request,response,this.handlerMethod);
//        System.out.println(executionChain);
        System.err.println("getPath () ...");
        return null;
    }

    @PostMapping("/postPath")
    public ModelAndView postPath(HttpServletRequest request, HttpServletResponse response,String name) throws Exception {
        System.out.println("  ---------   postPath  -------------  ");


        System.out.println("postPath () ...");
        return null;
    }

    @PutMapping("/putPath")
    public ModelAndView putPath(String name){
        System.out.println("putPath () ...");
        return null;
    }

    @RequestMapping("/requestPath")
    public ModelAndView requestPath(){
        System.out.println("requestPath () ...");
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
