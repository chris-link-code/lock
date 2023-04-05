package com.demo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/3
 */
@Slf4j
public class Tool {
    private static final Long K = 1L << 10;
    private static final Long M = 1L << 20;
    private static final Long G = 1L << 30;
    private static final Long T = 1L << 40;

    /**
     * 文件大小转换
     * TODO 不完善
     *
     * @param size 字节
     */
    public static String fileSizeTransfer(Long size) {
        if (size > T) {
            return (size / T) + "TB";
        }
        if (size > G) {
            return (size / G) + "GB";
        }
        if (size > M) {
            return (size / M) + "MB";
        }
        if (size > K) {
            return (size / K) + "KB";
        }
        return size + "B";
    }

    public static void logAllMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long useMemory = totalMemory - freeMemory;
        log.info("max memory: {}MB", maxMemory / M);
        log.info("total memory: {}MB", totalMemory / M);
        log.info("free memory: {}MB", freeMemory / M);
        log.info("use memory: {}MB", useMemory / M);
    }

    public static void logUseMemory() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long useMemory = totalMemory - freeMemory;
        log.info("use memory: {}MB", useMemory / M);
    }
}
