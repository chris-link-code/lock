package com.demo;

import com.demo.util.Tool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // 原文链接：https://blog.csdn.net/HaHa_Sir/article/details/84984234
        log.info("java.version: " + System.getProperty("java.version"));
        //log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        //log.info("os.arch: " + System.getProperty("os.arch"));

        //int processors = Runtime.getRuntime().availableProcessors();

        //log.info("processors: " + processors);

        // new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        //new FutureDemo().futureTest();

        //log.info(Convert.zConvert("PAYPALISHIRING", 3));

        //new GetCat().getAge();

        //new GetCatFuture().getAge();

        //new CompletableFutureDemo().futureTest();

        //new VolatileDemo().volatileAtomicity();

        //new VolatileDemo().volatileVisibility();

        //new CASDemo().atomicTest();

        //new CountSpeedDemo().calculate();

        //new LoopDemo().forTest();
        //new LoopDemo().whileTest();

        //new ThreadLocalDemo().test(processors);

        // 强、软、虚 四大引用
        //new ReferenceDemo().strongReference();
        //new ReferenceDemo().softReference();
        //new ReferenceDemo().weakReference();
        //new ReferenceDemo().phantomReference();

        //new ObjectDemo().test();

        //new TryDemo().test();

        //new ReentrantLockDemo().reentrantLockTest();
        //new ReadWriteLockDemo().readWriteLockTest();
        //new StampedLockDemo().test();

        //new ImplDemo().test();

        //log.info("end main()");

        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("total memory: {}", Tool.fileSizeTransfer(totalMemory));
        log.info("max memory: {}", Tool.fileSizeTransfer(maxMemory));
        log.info("free memory: {}", Tool.fileSizeTransfer(freeMemory));

    }
}