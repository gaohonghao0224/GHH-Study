package cn.itcast.order.service;

import cn.itcast.demo.feign.UserClient;
import cn.itcast.demo.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

//    @Autowired
//    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        User user = userClient.findUserById(order.getUserId());
        // 3. 封装 user
//        order.setUser(user);
        // 4.返回
        return order;
    }

    /**
     *  restTemplate 方式请求
     * @param orderId
     * @return
     */
//    public Order queryOrderById(Long orderId) {
//        // 1.查询订单
//        Order order = orderMapper.findById(orderId);
//
//        // 2. 利用 RestTemplate 发起 http请求，查询用户
//        String url = "http://user-server/user/" + order.getUserId();
//
//        User user = restTemplate.getForObject(url, User.class);
//        // 3. 封装 user
//        order.setUser(user);
//        // 4.返回
//        return order;
//    }

}
