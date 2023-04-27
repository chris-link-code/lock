package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author chris
 * @create 2023/4/27
 * <p>
 * 使用AQS自定义锁
 */
@Slf4j
public class SelfLockDemo {
    static final int LOOP_SIZE = 1_000;
    static int number = 0;
    static SelfLock lock = new SelfLock();

    public void test() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < LOOP_SIZE; i++) {
                number++;
            }
            count.countDown();
        }, "thread_1"
        );
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < LOOP_SIZE; i++) {
                number++;
            }
            count.countDown();
        }, "thread_2"
        );

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        try {
            count.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("number: {}", number);
    }
}
