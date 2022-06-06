package com.example.testmodel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootTest
class TestModelApplicationTests {

    @Autowired
    public ApplicationContext context;
    @Autowired
    public WebApplicationContext webApplicationContext;
    @Test
    void contextLoads() throws Exception {
        System.out.println("aaa");
        MockHttpServletRequest request = new MockHttpServletRequest("GET","/test");

        RequestMappingHandlerMapping handlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        HandlerExecutionChain executionChain = handlerMapping.getHandler(request);
        System.out.println(executionChain);
    }

}
