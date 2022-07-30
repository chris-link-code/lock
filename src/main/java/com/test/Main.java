package com.test;

import com.test.thread.CallableDemo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        //new ReadWriteLockDemo().readWriteLockTest();

        //new FutureDemo().futureTest();

        //log.info(Convert.zConvert("PAYPALISHIRING", 3));

        log.info("end main()");
    }
}