package com.demo;

import com.demo.proxy.Chicken;
import com.demo.proxy.ChickenDynamicProxy;
import com.demo.proxy.IStar;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author chris
 */
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

        //Tool.logUseMemory();
        //1GB
        //byte[] space = new byte[1 << 30];
        //log.info("new 1GB byte[]");
        //Tool.logUseMemory();

        /*
         * 静态代理
         */
        //IStar iStar = new Chicken();
        //new ChickenStaticProxy(iStar).sing("只因你太美");

        /*
         * 动态代理
         */
        InvocationHandler handler = new ChickenDynamicProxy(new Chicken());
        IStar iStar = (IStar) Proxy.newProxyInstance(IStar.class.getClassLoader(),
                new Class[]{IStar.class},
                handler);
        iStar.sing("只因你太美");
    }
}