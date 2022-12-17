package com.test.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2022/12/17
 */
@Slf4j
public class VolatileDemo {
    private int count = 16;

    public void volatileTest() {
        new Thread(() -> {
            for (int i = 0; i < 8; i++) {
                count--;
                log.info("count: {}", count);
            }
        }, "T_1").start();

        new Thread(() -> {
            for (int i = 0; i < 8; i++) {
                count--;
                log.info("count: {}", count);
            }
        }, "T_2").start();
    }
}
