package com.test;

import com.test.thread.CountSpeedDemo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // 原文链接：https://blog.csdn.net/HaHa_Sir/article/details/84984234
        log.info("java.version: " + System.getProperty("java.version"));
        //log.info("java.specification.version: " + System.getProperty("java.specification.version"));
        log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        //log.info("sun.arch.data.model: " + System.getProperty("sun.arch.data.model"));
        log.info("os.arch: " + System.getProperty("os.arch"));

        //int processors = Runtime.getRuntime().availableProcessors();
        //log.info("processors: " + processors);

        // new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        //new ReadWriteLockDemo().readWriteLockTest();

        //new FutureDemo().futureTest();

        //log.info(Convert.zConvert("PAYPALISHIRING", 3));

        //new GetCat().getAge();

        // new GetCatFeture().getAge();

        //new CompletableFutureDemo().futureTest();

        //new VolatileDemo().volatileAtomicity();

        //new VolatileDemo().volatileVisibility();

        //new CASDemo().atomicTest();

        new CountSpeedDemo().calculate();

        log.info("end main()");
    }
}