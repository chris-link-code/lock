package com.demo.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chris
 * @create 2023/3/23
 */
@Slf4j
public class ReentrantLockDemo {
    private static final int LOOP_SIZE = 8;
    Lock reentrantLock = new ReentrantLock();
    Map<Integer, String> map = new HashMap<>();

    public void reentrantLockRead(Integer key) {
        reentrantLock.lock();
        try {
            log.info("{} start read", Thread.currentThread().getName());
            log.info("{} read {}", key, map.get(key));
            log.info("{} end read", Thread.currentThread().getName());
        } finally {
            reentrantLock.unlock();
        }
    }

    public void reentrantLockWrite(Integer key) {
        reentrantLock.lock();
        try {
            log.info("{} start write", Thread.currentThread().getName());
            map.put(key, String.valueOf(key));
            log.info("{} write {}", key, key);
            log.info("{} end write", Thread.currentThread().getName());
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * ReentrantLock测试
     * 从本例可以看出，ReentrantLock锁不仅读写互斥，读读也互斥，效率低下
     */
    public void reentrantLockTest() {
        // 创建多个写线程
        for (int i = 0; i < LOOP_SIZE; i++) {
            int t = i;
            new Thread(() -> reentrantLockWrite(t), String.valueOf(i)).start();
        }

        // 创建多个读线程
        for (int i = 0; i < LOOP_SIZE; i++) {
            int t = i;
            new Thread(() -> reentrantLockRead(t), String.valueOf(i)).start();
        }
    }
}
