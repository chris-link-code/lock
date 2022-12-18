package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2022/12/17
 */
@Slf4j
public class VolatileDemo {
    private int count = 16;

    public void volatileTest() {
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                count--;
                log.info("count: {}", count);
            }
        }, "T_1").start();

        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                count--;
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("count: {}", count);
            }
        }, "T_2").start();
    }
}
