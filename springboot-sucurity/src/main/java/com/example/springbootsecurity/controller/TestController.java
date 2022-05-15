package com.example.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 16:14
 **/
@RestController
public class TestController {
    @GetMapping("/hello")
    public String printStr() {
        System.out.println("hello success");
        return "Hello success!";
    }
}
