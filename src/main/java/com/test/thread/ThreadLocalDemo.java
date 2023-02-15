package com.test.thread;

import com.test.bean.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chris
 * @create 2023/2/15
 */
@Slf4j
public class ThreadLocalDemo {
    public void test(int processors) {
        Data data = new Data();
        ExecutorService threadPool = Executors.newFixedThreadPool(processors);
        for (int i = 0; i < 24; i++) {
            threadPool.submit(() -> {
                try {
                    Integer before = data.number.get();
                    data.add();
                    Integer after = data.number.get();
                    log.info("{}\tbefore: {}\tafter: {}", Thread.currentThread().getName(), before, after);
                } finally {
                    // TODO 此处一定要在 finally 使用 remove()，否则线程不安全，详情见阿里规范
                    data.number.remove();
                }
            });
        }
        threadPool.shutdown();
    }
}
