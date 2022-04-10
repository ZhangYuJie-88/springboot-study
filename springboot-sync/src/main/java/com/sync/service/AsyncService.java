package com.sync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <h3>springboot-study</h3>
 * <p>模拟查找对应字符开头电影的方法</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-04-10 17:51
 **/
@Service
@Slf4j
public class AsyncService {
    private final List<String> movies =
            List.of(
                    "Forrest Gump",
                    "Titanic",
                    "Spirited Away",
                    "The Shawshank Redemption",
                    "Zootopia",
                    "Farewell ",
                    "Joker",
                    "Crawl");

    /**
     * 示范使用：找到特定字符开头的电影
     */
    @Async
    public CompletableFuture<List<String>> completableFutureTask1(String start) {
        // 打印日志
        log.warn(Thread.currentThread().getName() + "start this task!");
        // 找到特定字符/字符串开头的电影
        List<String> results =
                movies.stream().filter(movie -> movie.startsWith(start)).collect(Collectors.toList());
        // 模拟这是一个耗时的任务
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 返回一个已经用给定值完成的新的CompletableFuture。
        return CompletableFuture.completedFuture(results);
    }

    /**
     * 示范使用：找到特定字符开头的电影
     */
    @Async
    public void completableFutureTask2(String start) {
        // 打印日志
        log.warn(Thread.currentThread().getName() + "start this task!");
       // 这可以是插入数据库的操作等等
    }
}
