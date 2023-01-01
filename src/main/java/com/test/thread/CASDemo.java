package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chris
 * @create 2023/1/1
 */
@Slf4j
public class CASDemo {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void atomicTest() {
        int number = atomicInteger.incrementAndGet();
        log.info("Atomic Integer is {}", number);
        log.info("{}, {}", atomicInteger.compareAndSet(1, 2), atomicInteger.get());
        log.info("{}, {}", atomicInteger.compareAndSet(1, 3), atomicInteger.get());
    }

}
