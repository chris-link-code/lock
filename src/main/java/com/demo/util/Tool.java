package com.demo.util;

/**
 * @author chris
 * @create 2023/4/3
 */
public class Tool {


    /**
     * 文件大小转换
     * TODO 不完善
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
}
