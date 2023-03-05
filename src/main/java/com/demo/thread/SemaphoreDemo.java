package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2022/7/10
 */
@Slf4j
public class SemaphoreDemo {
    public void semaphoreTest() {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.info(Thread.currentThread().getName() + " is running");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    log.info(Thread.currentThread().getName() + " leave");
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
