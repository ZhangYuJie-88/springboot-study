package com.example.springboottestcontroller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-06-05 14:09
 **/
@RestController
public class TestController {
    private AtomicInteger num = new AtomicInteger(0);


    @GetMapping("/add_num")
    public void addNum() {
        // 获取先自增在获取当前值
        int andIncrement = num.incrementAndGet();
        System.out.println(andIncrement);
        System.out.println(num.get());
    }
}
