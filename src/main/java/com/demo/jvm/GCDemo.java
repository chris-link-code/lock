package com.demo.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/5/14
 */
@Slf4j
public class GCDemo {
    /**
     * Minor GC
     */
    public void allocate() {
        //10MB
        byte[] space = new byte[(1 << 20) * 10];
    }
}
