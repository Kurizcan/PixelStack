package com.pixelstack.ims.controller;

import com.pixelstack.ims.common.Auth.GetClientIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloController {

    @Autowired
    GetClientIp getClientIp;

    @RequestMapping("/hello")
    private String index(){
        return "Hello World!";
    }


}

