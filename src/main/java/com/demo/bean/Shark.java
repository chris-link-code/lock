package com.demo.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/14
 */
@Slf4j
public class Shark extends Fish {
    static {
        log.info("Shark 类被加载");
    }

    public Shark() {
    }
}
