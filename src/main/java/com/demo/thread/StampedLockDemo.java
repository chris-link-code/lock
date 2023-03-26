package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author chris
 * @create 2023/3/25
 * https://perfectcode.top/2022/06/10/ReentrantLock%E3%80%81ReentrantReadWriteLock%E3%80%81StampedLock%E8%AE%B2%E8%A7%A3/
 * StampedLock = ReentrantReadWriteLock + 读的过程中也允许获取写锁介入
 */
@Slf4j
public class StampedLockDemo {
    int number = 1;
    private static final int LOOP_SIZE = 8;
    StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        try {
            log.info("{} start write, number: {}, stamp: {}",
                    Thread.currentThread().getName(), number, stamp);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            number = number + 1;
        } finally {
            log.info("{} write end, number: {}, stamp: {}",
                    Thread.currentThread().getName(), number, stamp);
            stampedLock.unlockWrite(stamp);
        }
    }

    public void read() {
        long stamp = stampedLock.readLock();
        try {
            for (int i = 0; i < LOOP_SIZE; i++) {
                log.info("{} {} time read, number: {}, stamp: {}",
                        Thread.currentThread().getName(), i, number, stamp);
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("{} read end, number: {}, stamp: {}",
                    Thread.currentThread().getName(), number, stamp);
            stampedLock.unlockRead(stamp);
        }
    }

    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();

        /*try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }*/

        if (stampedLock.validate(stamp)) {
            log.info("{} tryOptimisticRead, number: {}, stamp: {}, validate(stamp): {} (true无修改，false有修改)",
                    Thread.currentThread().getName(), number, stamp, stampedLock.validate(stamp));
        } else {
            log.info("有人修改过，有写操作，从乐观读升级为悲观读，并重新获取数据");
            stamp = stampedLock.readLock();
            try {
                log.info("{} tryOptimisticRead, number: {}, stamp: {}, validate(stamp): {} (true无修改，false有修改)",
                        Thread.currentThread().getName(), number, stamp, stampedLock.validate(stamp));
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        log.info("{} tryOptimisticRead end, number: {}, stamp: {}",
                Thread.currentThread().getName(), number, stamp);
    }

    //乐观读，读的过程中也允许获取写锁介入
    /*public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
        System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取... " + i + " 秒" +
                    "后stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改过------有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t" + " finally value: " + result);
    }*/


    public void test() {
        //new Thread(() -> read(), "read_thread").start();
        //new Thread(() -> tryOptimisticRead(), "optimistic_read_thread").start();
        for (int i = 0; i < LOOP_SIZE; i++) {
            new Thread(() -> tryOptimisticRead(), "optimistic_read_thread").start();
        }
        new Thread(() -> write(), "write_thread").start();
    }
}