package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author chris
 * @create 2022/7/10
 */
@Slf4j
public class CallableDemo {
    public void callableTest() {
        Callable<String> callable = () -> {
            String currentThread = Thread.currentThread().getName();
            return currentThread + " is running";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);

        new Thread(futureTask, "A").start();

        if (futureTask.isDone()) {
            log.info("future task is done");
        } else {
            log.info("future task is not done");
        }

        try {
            log.info(futureTask.get());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }
}
