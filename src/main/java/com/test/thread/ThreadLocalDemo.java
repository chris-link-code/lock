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
    /**
     * 使用ThreadLocal记住如下三步：
     * 一定要初始化，ThreadLocal.withInitial(() -> 初始值)，否则会报空指针异常;
     * 建议把ThreadLocal修饰为static;
     * 使用完一定要记得手动remove
     */
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
                    // 此处一定要在 finally 使用 remove()，否则线程不安全，详情见阿里规范
                    data.number.remove();
                }
            });
        }
        threadPool.shutdown();
    }
}
