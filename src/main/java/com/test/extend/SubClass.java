package com.test.extend;

/**
 * @author chris
 * @create 2022/7/20
 */
public class SubClass extends SuperClass {
    private int n;

    public SubClass() {
        // 自动调用父类的无参数构造器
        System.out.println("SubClass()");
    }

    public SubClass(int n) {
        // 调用父类中带有参数的构造器
        super(300);
        System.out.println("SubClass(int n):" + n);
        this.n = n;
    }
}
