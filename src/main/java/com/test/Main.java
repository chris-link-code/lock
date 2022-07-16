package com.test;

import com.test.thread.*;

public class Main {
    public static void main(String[] args) {
        //new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        new ReadWriteLockDemo().readWriteLockTest();
    }
}