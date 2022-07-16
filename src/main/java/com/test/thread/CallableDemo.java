package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author chris
 * @create 2022/7/10
 */
@Slf4j
public class CallableDemo {
    public void callableTest() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return Thread.currentThread().getName() + " is running callable";
            }
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);

        if (futureTask.isDone()) {
            log.info("future task is done");
        } else {
            log.info("future task is not done");
        }

        new Thread(futureTask, "A").start();

        while (!futureTask.isDone()) {
        }

        if (futureTask.isDone()) {
            log.info("future task is done");
        } else {
            log.info("future task is not done");
        }

        try {
            log.info(futureTask.get());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }
}
