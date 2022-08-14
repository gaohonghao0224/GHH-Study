package com.example.provider.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("fei")
@Service
public interface conummerClient {
//
//    @GetMapping("/screet/sysdep/testConnect")
//    public void connent();

   @GetMapping("/ra/system/sysdep/testConnect")
    public void connent();
}
