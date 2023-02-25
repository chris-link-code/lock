package com.test.bean;

/**
 * @author chris
 * @create 2023/2/25
 */
public class Self {
    /**
     * 此方法在gc清理之前执行
     * 一般情况不要重写此方法
     * 此处只作为演示
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("invoke finalize()");
    }
}
