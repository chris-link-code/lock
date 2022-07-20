package com.test.extend;

/**
 * @author chris
 * @create 2022/7/20
 */
public class SuperClass {
    private int n;

    public SuperClass() {
        System.out.println("SuperClass()");
    }

    SuperClass(int n) {
        System.out.println("SuperClass(int n)");
        this.n = n;
    }
}
