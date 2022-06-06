package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId, @RequestHeader(value = "girl",required = false)String girl, @RequestHeader(value = "name",required = false)String name) {
        System.err.println("name   " + name);
        System.err.println("girl   " + girl);
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }
}
