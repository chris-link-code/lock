package com.test;

import com.test.extend.SubClass;
import com.test.extend.SubClass2;
import com.test.thread.FutureDemo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        //new CallableDemo().callableTest();

        //new CountDownLatchDemo().countDownLatchTest();

        //new CyclicBarrierDemo().cyclicBarrierTest();

        //new SemaphoreDemo().semaphoreTest();

        //new ReadWriteLockDemo().readWriteLockTest();

        // TODO 创建没有构造函数的对象时会怎么样
        new FutureDemo().futureTest();

        //extendsTest();


        log.info("end main()");
    }

    /**
     * 测试继承
     * 子类会自动调用父类的无参构造
     * 调用父类的有参构造需要用super()
     * 如果子类调用了父类得有参构造，则不会自动调用父类的无参构造
     */
    private static void extendsTest() {
        System.out.println("------SubClass 类继承------");
        System.out.println("new SubClass():");
        SubClass sc1 = new SubClass();
        System.out.println();

        System.out.println("new SubClass(100):");
        SubClass sc2 = new SubClass(100);
        System.out.println();

        System.out.println("------SubClass2 类继承------");
        System.out.println("new SubClas2s():");
        SubClass2 sc3 = new SubClass2();
        System.out.println();

        System.out.println("new SubClass(200):");
        SubClass2 sc4 = new SubClass2(200);
    }
}