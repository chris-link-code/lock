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
    StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        log.info("{} start write", Thread.currentThread().getName());
        try {
            number = number + 1;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        log.info("{} write end", Thread.currentThread().getName());
    }

    /**
     * 悲观读，读没有完成时候写锁无法获得锁
     */
    public void read() {
        long stamp = stampedLock.readLock();
        log.info("{} come in readLock code block，4 seconds continue...",
                Thread.currentThread().getName());
        for (int i = 0; i < 4; i++) {
            //暂停几秒钟线程
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("{} is reading ...", Thread.currentThread().getName());
        }

        try {
            log.info("{} get number：{}", Thread.currentThread().getName(), number);
            log.info("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    /**
     * 乐观读，读的过程中也允许获取写锁介入
     */
    public void tryOptimisticRead() {
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
    }


    public void test() {
        /*传统版
        new Thread(() -> {
            resource.read();
        },"readThread").start();
 
        //暂停几秒钟线程
        try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
 
        new Thread(() -> {
            log.info(Thread.currentThread().getName()+"\t"+"----come in");
            resource.write();
        },"writeThread").start();
 
        //暂停几秒钟线程
        try { TimeUnit.MILLISECONDS.sleep(400); } catch (InterruptedException e) { e.printStackTrace(); }
 
        log.info(Thread.currentThread().getName()+"\t"+"number:" +number);
        */

        new Thread(() -> tryOptimisticRead(), "read_thread").start();

        try {
            //暂停2秒钟线程,读过程可以写介入，演示
            //TimeUnit.MILLISECONDS.sleep(200);
            //暂停6秒钟线程
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        new Thread(() -> write(), "write_thread").start();
    }
}