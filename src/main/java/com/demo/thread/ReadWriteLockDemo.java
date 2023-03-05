package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chris
 * @create 2022/7/10
 * <p>
 * 读写锁测试
 * 读锁是共享锁
 * 写锁是独占锁
 */
@Slf4j
public class ReadWriteLockDemo {
    private volatile Map<Integer, String> map = new HashMap<>(1 << 4);
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void readWriteLockTest() {
        // write
        for (int i = 0; i < 5; i++) {
            final int number = i;
            new Thread(() -> {
                write(number, String.valueOf(number));
            }, String.valueOf(i)).start();
        }

        //read
        for (int i = 0; i < 5; i++) {
            final int number = i;
            new Thread(() -> {
                read(number);
            }, String.valueOf(i)).start();
        }
    }

    /**write*/
    private void write(int key, String value) {
        lock.writeLock().lock();
        log.info("{} is writing", Thread.currentThread().getName());
        map.put(key, value);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info(Thread.currentThread().getName() + " is write done");
        lock.writeLock().unlock();
    }

    /**read*/
    private void read(int key) {
        lock.readLock().lock();
        log.info("{} is reading", Thread.currentThread().getName());
        map.get(key);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info(Thread.currentThread().getName() + " is read done");
        lock.readLock().unlock();
    }

}
