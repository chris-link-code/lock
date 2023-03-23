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
public class LockDemo {
    private static final int LOOP_SIZE = 8;
    Lock reentrantLock = new ReentrantLock();
    Map<Integer, String> map = new HashMap<>();

    public void reentrantLockRead(Integer key) {
        boolean hasLock = reentrantLock.tryLock();
        if (!hasLock) {
            log.warn("ReentrantLockRead lock failed");
            return;
        }
        try {
            log.info("{} read {}", key, map.get(key));
        } finally {
            reentrantLock.unlock();
        }
    }

    public void reentrantLockWrite(Integer key) {
        boolean hasLock = reentrantLock.tryLock();
        if (!hasLock) {
            log.warn("ReentrantLockWrite lock failed");
            return;
        }
        try {
            map.put(key, String.valueOf(key));
            log.info("{} write {}", key, key);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * ReentrantLock测试
     */
    public void reentrantLockTest() {
        // 创建多个写线程
        for (int i = 0; i < LOOP_SIZE; i++) {
            int t = i;
            new Thread(() -> {
                log.info("{} start write", t);
                reentrantLockWrite(t);
                log.info("{} end write", t);
            }, String.valueOf(i)).start();
        }

        // 创建多个读线程
        for (int i = 0; i < LOOP_SIZE; i++) {
            int t = i;
            new Thread(() -> {
                log.info("{} start read", t);
                reentrantLockRead(t);
                log.info("{} end read", t);
            }, String.valueOf(i)).start();
        }
    }
}
