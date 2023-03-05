package com.demo.thread;

import com.demo.bean.Cat;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author chris
 * @create 2022/7/31
 * <p>
 * 多线程案例
 */
@Slf4j
public class GetCatFeture {
    public void getAge() {
        long start = System.currentTimeMillis();
        List<Cat> list = Arrays.asList(
                new Cat("Tom"),
                new Cat("Jerry"),
                new Cat("Lily"),
                new Cat("Brown"),
                new Cat("Cherry"),
                new Cat("Alen"),
                new Cat("Frank"),
                new Cat("Kate")
        );

        List<String> collect = list.stream().map(cat ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("cat %s age is %d", cat.getName(), cat.getAge())))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());

        for (String item : collect) {
            log.info(item);
        }
        long end = System.currentTimeMillis();
        log.info("spend time " + (end - start));
    }
}
