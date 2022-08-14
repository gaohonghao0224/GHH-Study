package com.example.provider.controller;

import com.example.provider.feign.conummerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TryController {

    @Autowired
    private conummerClient conummerClient;

    @GetMapping("try/comment")
    public  void comment(){
        conummerClient.connent();
    }
}
