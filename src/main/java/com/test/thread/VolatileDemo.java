package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2022/12/17
 */
@Slf4j
public class VolatileDemo {
    private volatile int count = 0;

    /**
     * volatile不具有原子性
     */
    public void volatileTest() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                    count++;
                    if (count > 8000) {
                        log.info("count: {}", count);
                    }
                }
            }, "T_" + i).start();
        }
    }


    public void volatileTest1() {
        for (int i = 0; i < 10_000; i++) {
            new Thread(() -> {
                int x = 1;
                int y = 2;
                x = x + x;
                y = x + y;
                if (y == 3) {
                    log.info("y: {}", y);
                }
            }, "T" + i).start();
        }
    }
}
