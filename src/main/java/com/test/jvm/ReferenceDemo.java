package com.test.jvm;

import com.test.bean.Self;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;

/**
 * @author chris
 * @create 2023/2/25
 */
@Slf4j
public class ReferenceDemo {
    /**
     * 强引用
     */
    public void strongReference() {
        Self self = new Self();
        log.info("gc before: {}", self);
        // 将引用置为为null后，gc时就会被清理
        self = null;
        // 手动开启gc，一般情况不要使用
        System.gc();
        log.info("gc after: {}", self);
    }

    /**
     * 软引用
     * 当系统内存充足时，gc不会回收
     * 当系统内存不充足时，gc会回收
     */
    public void softReference() {
        SoftReference<Self> self = new SoftReference<>(new Self());
        log.info("soft reference: {}", self.get());
        // 手动开启gc，一般情况不要使用
        System.gc();
        log.info("gc after: {}", self.get());
    }
}
