package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author chris
 * @create 2022/7/10
 */
@Slf4j
public class CountDownLatchDemo {
    /**
     * 根据CountDownLatch判断所有线程全部执行完毕
     */
    public void countDownLatchTest() {
        CountDownLatch count = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info(Thread.currentThread().getName() + " is running");
                count.countDown();
            }, String.valueOf(i)).start();
        }

        try {
            count.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("threads are done");
    }
}
