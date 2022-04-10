package com.sync.controller;

import com.sync.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * <h3>springboot-study</h3>
 * <p> 测试编写的异步方法</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-04-10 17:55
 **/
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {

    private final AsyncService asyncService;

    @GetMapping("/movies")
    public String completableFutureTask1() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 开始执行大量的异步任务
        List<String> words = List.of("F", "T", "S", "Z", "J", "C");
        List<CompletableFuture<List<String>>> completableFutureList =
                words.stream()
                        .map(asyncService::completableFutureTask1)
                        .collect(Collectors.toList());
        // CompletableFuture.join（）方法可以获取他们的结果并将结果连接起来
        List<List<String>> results = completableFutureList.stream()
                .map(CompletableFuture::join).collect(Collectors.toList());
        stopWatch.stop();
        // 打印结果以及运行程序运行花费时间
        System.out.println("耗时: " + stopWatch.getTotalTimeMillis());
        return results.toString();
    }

    @GetMapping("/movies2")
    public String completableFutureTask2() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> words = List.of("F", "T", "S", "Z", "J", "C");
        words.forEach(asyncService::completableFutureTask2);
        stopWatch.stop();
        // 打印结果以及运行程序运行花费时间
        System.out.println("耗时: " + stopWatch.getTotalTimeMillis());
        return "Done";
    }
}
