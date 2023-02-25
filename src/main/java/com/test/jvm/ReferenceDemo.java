package com.test.jvm;

import com.test.bean.Self;
import lombok.extern.slf4j.Slf4j;

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
}
