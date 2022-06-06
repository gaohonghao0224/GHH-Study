package springMVC.a20;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  为了调用 invoke方法
 */
@Component
public class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
    /**
     *  RequestMappingHandlerAdapter 是父类
     *      就是为了调用控制器方方法
     * @param request
     * @param response
     * @param handlerMethod
     * @return
     * @throws Exception
     */
    @Override
    public ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        return super.invokeHandlerMethod(request, response, handlerMethod);
    }
}
