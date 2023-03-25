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
        try {
            for (int i = 0; i < LOOP_SIZE; i++) {
                if (!stampedLock.validate(stamp)) {
                    log.info("{} {} time tryOptimisticRead, number: {}, stamp: {}, stampedLock.validate(stamp): {} (true无修改，false有修改)",
                            Thread.currentThread().getName(), i, number, stamp, stampedLock.validate(stamp));
                    TimeUnit.MILLISECONDS.sleep(50);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("{} tryOptimisticRead end, number: {}, stamp: {}",
                    Thread.currentThread().getName(), number, stamp);
            stampedLock.unlockRead(stamp);
        }

        /*
        //先获得乐观读锁的戳
        long stamp = stampedLock.tryOptimisticRead();
        //故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
        log.info("{} start tryOptimisticRead, stampedLock.validate(stamp): {} (true无修改，false有修改)",
                Thread.currentThread().getName(),
                stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("{} is reading... stampedLock.validate(stamp): {} (true无修改，false有修改)",
                    Thread.currentThread().getName(), stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            log.info("有人修改过 ---- 有写操作");
            stamp = stampedLock.readLock();
            try {
                log.info("从 乐观读 升级为 悲观读");
                log.info("重新悲观读后 number: {}", number);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        log.info("{} finally number: {}",
                Thread.currentThread().getName(), number);
        */
    }


    public void test() {
        //new Thread(() -> read(), "read_thread").start();
        new Thread(() -> tryOptimisticRead(), "optimistic_read_thread").start();
        new Thread(() -> write(), "write_thread").start();
    }
}