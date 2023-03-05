package com.demo.thread;

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
    public void volatileAtomicity() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    // count++是非原子操作，包括"加载，计算，赋值"三步
                    count++;
                }
            }, "T_" + i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("count: {}", count);
    }

    private volatile boolean flag = true;

    /**
     * 证明volatile的可见性
     * 如果flag不加volatile修饰，T_1线程会一直执行
     */
    public void volatileVisibility() {
        new Thread(() -> {
            log.info("flag: {}", flag);
            while (flag) {
            }
            log.info("Thread is complete");
        }, "T_1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        new Thread(() -> {
            flag = false;
            log.info("flag: {}", flag);
            log.info("Thread is complete");
        }, "T_2").start();

        log.info("end flag: {}", flag);
    }


    /**
     * 证明volatile的有序性
     */
    public void volatileOrder() {
    }
    
}
