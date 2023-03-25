package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
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
    private final Map<Integer, String> map = new HashMap<>(1 << 4);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final int LOOP_SIZE = 8;

    /**
     * 从本例可以看出，ReentrantReadWriteLock锁读写互斥，写写互斥，但读读不互斥，效率较高
     */
    public void readWriteLockTest() {
        // write
        for (int i = 0; i < LOOP_SIZE; i++) {
            final int number = i;
            new Thread(() -> write(number, String.valueOf(number)), String.valueOf(i)).start();
        }

        //read
        for (int i = 0; i < LOOP_SIZE; i++) {
            final int number = i;
            new Thread(() -> read(number), String.valueOf(i)).start();
        }
    }

    /**
     * write
     */
    private void write(int key, String value) {
        lock.writeLock().lock();
        try {
            log.info("{} is writing", Thread.currentThread().getName());
            map.put(key, value);
            log.info(Thread.currentThread().getName() + " is write done");
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * read
     */
    private void read(int key) {
        lock.readLock().lock();
        try {
            log.info("{} is reading", Thread.currentThread().getName());
            log.info("{} value is {}", key, map.get(key));
            log.info(Thread.currentThread().getName() + " read done");
        } finally {
            lock.readLock().unlock();
        }
    }

}
