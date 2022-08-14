package com.example.conmmer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectController {

    @GetMapping("test/connect")
    public void connect(){
        System.out.println("connect");
    }
}
