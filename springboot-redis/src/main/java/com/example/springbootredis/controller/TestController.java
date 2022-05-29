package com.example.springbootredis.controller;

import com.example.springbootredis.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 18:08
 **/
@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/ip_test")
    public Result<String> tabCnt() {
        return Result.success("成功");
    }
}
