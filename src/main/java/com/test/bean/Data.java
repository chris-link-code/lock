package com.test.bean;

/**
 * @author chris
 * @create 2023/2/15
 * <p>
 * ThreadLocalDemo资源类
 */
public class Data {
    public static ThreadLocal<Integer> number = ThreadLocal.withInitial(() -> 0);

    public void add() {
        number.set(number.get() + 1);
    }
}
