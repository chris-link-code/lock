package com.demo.others;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chris
 * @create 2023/1/15
 *
 * 测试for和while速度对比;
 *
 * 结论：
 * 当循环次数固定时，强烈推荐使用for，编译器会优化，速度快很多;
 * 当循环次数不固定时，for和while速度基本无差别，建议使用while;
 */
@Slf4j
public class LoopDemo {
    static final int LOOP_SIZE = 100_000_000;

    public void forTest() {
        long start = System.currentTimeMillis();
        boolean keep = true;
        AtomicInteger atomicInteger = new AtomicInteger();
        for (; ; ) {
            if (atomicInteger.get() > LOOP_SIZE) {
                break;
            }
            atomicInteger.incrementAndGet();
        }
        long end = System.currentTimeMillis();
        log.info("for time: {}ms", end - start);
    }

    public void whileTest() {
        long start = System.currentTimeMillis();
        boolean keep = true;
        AtomicInteger atomicInteger = new AtomicInteger();
        while (keep) {
            if (atomicInteger.get() > LOOP_SIZE) {
                keep = false;
            }
            atomicInteger.incrementAndGet();
        }
        long end = System.currentTimeMillis();
        log.info("while time: {}ms", end - start);
    }
}
