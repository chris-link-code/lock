package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chris
 * @create 2022/7/10
 */
@Slf4j
public class CyclicBarrierDemo {
    public void cyclicBarrierTest() {
        final int NUMBER = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
            log.info(Thread.currentThread().getName() + " is done");
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info(Thread.currentThread().getName() + " is running");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                } catch (BrokenBarrierException e) {
                    log.error(e.getMessage(), e);
                }
            }, String.valueOf(i)).start();
        }
    }
}
