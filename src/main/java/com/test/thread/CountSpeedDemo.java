package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author chris
 * @create 2023/1/3
 * 多线程累加
 * LongAdder和LongAccumulator远远快于其他方法
 * synchronized的i++最慢
 */
@Slf4j
public class CountSpeedDemo {
    private final static int THREAD_COUNT = 16;
    private final static int LOOP_COUNT = 1_000_000;

    int number = 0;

    /**
     * synchronized
     * number++
     */
    public synchronized void plusPlus() {
        CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < LOOP_COUNT; j++) {
                        synchronized (CountSpeedDemo.class) {
                            number++;
                        }
                    }
                } finally {
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("result: {}, spend {}ms of plusPlus()", number, (end - start));
    }

    AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * AtomicLong
     */
    public void atomicIntegerIncrease() {
        CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < LOOP_COUNT; j++) {
                        atomicInteger.incrementAndGet();
                    }
                } finally {
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("result: {}, spend {}ms of atomicIntegerIncrease()", atomicInteger.get(), (end - start));
    }

    AtomicLong atomicLong = new AtomicLong(0);

    /**
     * AtomicLong
     */
    public void atomicLongIncrease() {
        CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < LOOP_COUNT; j++) {
                        atomicLong.incrementAndGet();
                    }
                } finally {
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("result: {}, spend {}ms of atomicLongIncrease()", atomicLong.get(), (end - start));
    }

    LongAdder longAdder = new LongAdder();

    /**
     * LongAdder
     */
    public void longAdd() {
        CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < LOOP_COUNT; j++) {
                        longAdder.increment();
                    }
                } finally {
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("result: {}, spend {}ms of longAdd()", longAdder.sum(), (end - start));
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    /**
     * LongAccumulator
     */
    public void longAccumulate() {
        CountDownLatch count = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < LOOP_COUNT; j++) {
                        longAccumulator.accumulate(1);
                    }
                } finally {
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("result: {}, spend {}ms of longAccumulate()", longAccumulator.get(), (end - start));
    }

    public void calculate() {
        plusPlus();
        atomicIntegerIncrease();
        atomicLongIncrease();
        longAdd();
        longAccumulate();
    }
}
