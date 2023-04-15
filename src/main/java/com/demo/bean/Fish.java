package com.demo.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/14
 */
@Slf4j
public class Fish {
    public static int i;
    public static final int L = 1;

    static {
        log.info("Fish 类被加载");
    }
}
