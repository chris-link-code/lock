package com.demo.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chris
 * @create 2023/4/27
 * <p>
 * 使用AQS自定义锁
 * https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html
 */
public class SelfLock {
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }
}
