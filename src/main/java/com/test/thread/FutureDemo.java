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
        int processors;
        ExecutorService executor = null;
        try {
            processors = Runtime.getRuntime().availableProcessors();
            log.info("processors: " + processors);

            executor = Executors.newFixedThreadPool(processors);

            //runAsync(executor);
            //supplyAsync(executor);
            whenSupplyAsync(executor);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (executor != null) {
                executor.shutdown();
                log.info("executor shutdown");
            }
        }
    }

    /**
     * 没有返回值的异步
     *
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
     *
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

        try {
            log.info(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("do something else");
        log.info("end supplyAsync()");
    }

    public void whenSupplyAsync(Executor executor) {
        log.info("whenSupplyAsync()");

        // 不要用new创建CompletableFuture，用runAsync()、supplyAsync()方法
        // Future<String> future = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            return "This is whenSupplyAsync()";
            //此处要使用线程池，否则如果主线程先结束，默认线程池会自动关闭，则不会执行whenComplete内的内容
        }, executor).whenComplete((v, e) -> {
            if (e == null) {
                log.info("run complete, get --> " + v);
            }
        }).exceptionally(e -> {
            log.error(e.getMessage(), e);
            return null;
        });

        log.info("do something else");
        log.info("end whenSupplyAsync()");
    }
}
