package com.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author chris
 * @create 2022/8/8
 */
@Slf4j
public class CompletableFutureDemo {

    public void futureTest() {
        int processors;
        ExecutorService executor = null;
        try {
            processors = Runtime.getRuntime().availableProcessors();
            log.info("processors: " + processors);
            executor = Executors.newFixedThreadPool(processors);

            //supply(executor);
            handle(executor);
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
     * 多个步骤都是由同一线程执行
     * 若其中一步报错，则程序终止
     * @param executor 线程池
     */
    public void supply(Executor executor) {
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 1");
            return 1;
        }, executor).thenApply(v -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            int i = 1 / 0;
            log.info("step 2");
            return v + 2;
        }).thenApply(v -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 3");
            return v + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                long end = System.currentTimeMillis();
                log.info("spend time " + (end - start) + "ms complete\t" + v);
            }
        }).exceptionally(e -> {
            log.error(e.getMessage(), e);
            return null;
        });
        log.info(Thread.currentThread().getName() + " is running");
    }

    /**
     * 多个步骤都是由同一线程执行
     * 若其中一步报错，剩下的步骤依然可以继续执行
     * @param executor 线程池
     */
    public void handle(Executor executor) {
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 1");
            return 1;
        }, executor).handle((v,e) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException ex) {
                log.error(e.getMessage(), e);
            }
            int i = 1 / 0;
            log.info("step 2");
            return v + 2;
        }).handle((v,e) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException ex) {
                log.error(e.getMessage(), e);
            }
            log.info("step 3");
            return v + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                long end = System.currentTimeMillis();
                log.info("spend time " + (end - start) + "ms complete\t" + v);
            }
        }).exceptionally(e -> {
            log.error(e.getMessage(), e);
            return null;
        });
        log.info(Thread.currentThread().getName() + " is running");
    }
}
