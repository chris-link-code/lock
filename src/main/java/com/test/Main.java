package com.test;

import com.test.thread.GetCatFeture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors: " + processors);

        // new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        //new ReadWriteLockDemo().readWriteLockTest();

        //new FutureDemo().futureTest();

        //log.info(Convert.zConvert("PAYPALISHIRING", 3));

        //new GetCat().getAge();

        new GetCatFeture().getAge();

        log.info("end main()");
    }
}