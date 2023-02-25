package com.test.jvm;

import com.test.bean.Self;

/**
 * @author chris
 * @create 2023/2/25
 */
public class ReferenceDemo {
    public void test() {
        Self self = new Self();
        System.out.println("gc before: " + self);
        // 将引用置为为null后，gc时就会被清理
        self = null;
        System.gc();
        System.out.println("gc after: " + self);
    }
}
