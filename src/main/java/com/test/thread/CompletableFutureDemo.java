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

            completable(executor);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (executor != null) {
                executor.shutdown();
                log.info("executor shutdown");
            }
        }
    }

    public void completable(Executor executor) {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 1");
            return 1;
        }, executor).thenApply(v -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 2");
            return v + 2;
        }).thenApply(v -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("step 3");
            return v + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                log.info("complete\t" + v);
            }
        }).exceptionally(e -> {
            log.error(e.getMessage(), e);
            return null;
        });

        log.info(Thread.currentThread().getName() + " is running");
    }
}
