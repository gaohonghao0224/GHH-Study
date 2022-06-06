package cn.itcast.demo.feign;

import cn.itcast.demo.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *  抽取 服务通用的api ， 重复调用
 *      别的服务依靠 jar包导入此服务，获取 client 的信息
 *
 *      但是别的服务的包肯定不在 spring的包扫描路径里面，没办法生成实例到ioc容器中，也就无法注入
 *      这时候就要使用
 *          @EnableFeignClients(basePackages = "路径")  扫描指定包路径下所有的类
 *          @EnableFeignClients(clients = {类.class})
 */
@FeignClient("user-server")
public interface UserClient {
    @GetMapping("/user/{id}")
    User findUserById(@PathVariable("id") Long id);
}
