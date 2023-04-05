package com.demo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/3
 */
@Slf4j
public class Tool {


    /**
     * 文件大小转换
     * TODO 不完善
     *
     * @param size 字节
     */
    public static String fileSizeTransfer(Long size) {
        Long KB = 1L << 10;
        Long MB = 1L << 20;
        Long GB = 1L << 30;
        Long TB = 1L << 40;
        if (size > TB) {
            return (size / TB) + "TB";
        }
        if (size > GB) {
            return (size / GB) + "GB";
        }
        if (size > MB) {
            return (size / MB) + "MB";
        }
        if (size > KB) {
            return (size / KB) + "KB";
        }
        return size + "B";
    }

    public static void logMemory() {
        int mb = 1 << 20;
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("total memory: {}", totalMemory / mb);
        log.info("max memory: {}", maxMemory / mb);
        log.info("free memory: {}", freeMemory / mb);
    }
}
