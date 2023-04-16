package com.demo.jvm;

import com.demo.bean.Fish;
import com.demo.bean.Shark;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/14
 * <p>
 * 类加载测试
 */
@Slf4j
public class ClassLoadDemo {
    static {
        log.info("ClassLoadDemo 类被加载");
    }

    public void newChild() {
        Shark shark = new Shark();
        log.info("结论：创建子类对象时，会先加载父类");
    }

    public void staticInt() {
        log.info("调用static int: {}", Fish.i);
        log.info("结论：调用static int时，会加载类，未被初始化的int值为0");
    }

    public void staticFinalInt() {
        log.info("调用static final int: {}", Fish.L);
        log.info("结论：调用static final int时，不会加载类");
    }

    public void reflect() throws ClassNotFoundException {
        Class clazz = Class.forName("com.demo.bean.Shark");
        log.info("反射: {}", clazz);
        log.info("结论：调用反射获取class时，和创建对象一样，先加载父类，再加载子类");
    }

    public void extendStatic() {
        log.info("通过子类调用父类的静态变量: {}", Shark.i);
        log.info("结论：通过子类调用父类的静态变量时，不会加载子类");
    }

    public void extendFinal() {
        log.info("通过子类调用父类的常量: {}", Shark.L);
        log.info("结论：通过子类调用父类的常量时，不会加载父类和子类");
    }

    public void array() {
        Shark[] sharks = new Shark[3];
        log.info("数组: {}", sharks.toString());
        log.info("结论：调用数组时，不会加载父类和子类");
    }

    public void classLoader() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();
        log.info("ClassLoader.getSystemClassLoader: {}", systemClassLoader);
        log.info("ClassLoader.getPlatformClassLoader: {}", platformClassLoader);

        ClassLoader systemParent = systemClassLoader.getParent();
        ClassLoader platformParent = platformClassLoader.getParent();
        log.info("systemParent: {}", systemParent);
        log.info("platformParent: {}", platformParent);
    }
}
