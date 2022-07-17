package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author chris
 * @create 2022/7/17
 */
@Slf4j
public class FutureDemo {
    public void futureTest() {
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors: " + processors);
        ExecutorService executor = Executors.newFixedThreadPool(processors);

        //runAsync(executor);
        supplyAsync(executor);

        executor.shutdown();
    }

    /**
     * 没有返回值的异步
     * @param executor
     */
    public void runAsync(Executor executor) {
        log.info("runAsync()");

        // 不要用new创建CompletableFuture，用runAsync()、supplyAsync()方法
        // Future<String> future = new CompletableFuture<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }, executor);

        try {
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("end runAsync()");
    }

    /**
     * 有返回值的异步
     * @param executor
     */
    public void supplyAsync(Executor executor) {
        log.info("supplyAsync()");

        // 不要用new创建CompletableFuture，用runAsync()、supplyAsync()方法
        // Future<String> future = new CompletableFuture<>();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            return "This is supplyAsync()";
        }, executor);

        log.info("do something else");

        try {
            log.info(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("end supplyAsync()");
    }

    public void whenSupplyAsync(Executor executor) {
        log.info("supplyAsync()");

        // 不要用new创建CompletableFuture，用runAsync()、supplyAsync()方法
        // Future<String> future = new CompletableFuture<>();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            return "This is supplyAsync()";
        }, executor);

        log.info("do something else");

        try {
            log.info(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("end supplyAsync()");
    }
}
