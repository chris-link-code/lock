package com.demo.jvm;

import com.demo.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chris
 * @create 2023/4/22
 * <p>
 * 反射
 */
@Slf4j
public class ReflectDemo {
    static final Integer LOOP_SIZE = 1 << 30;

    /**
     * 通过反射获取class对象
     *
     * @throws ClassNotFoundException
     */
    public void getClazz() throws ClassNotFoundException {
        /*
         * 获取class的三种方式
         * 1. Class.forName("包名.类名");
         * 2. 类名.class;
         * 3. 对象.getClass();
         * 1最为常用
         * 2一般当作参数进行传递
         * 3当已经有了对象时才用
         */
        Class clazz1 = Class.forName("com.demo.bean.User");
        Class clazz2 = User.class;

        User user = new User();
        Class<? extends User> clazz3 = user.getClass();

        log.info("clazz1: {}", clazz1);
        log.info("clazz2: {}", clazz2);
        log.info("clazz3: {}", clazz3);

        log.info("clazz1 == clazz2: {}", clazz1 == clazz2);
        log.info("clazz2 == clazz3: {}", clazz2 == clazz3);
    }

    public void test() throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Class clazz = Class.forName("com.demo.bean.User");
        User user = (User) clazz.getDeclaredConstructor().newInstance();
        log.info(user.toString());
    }


    /**
     * 测试反射性能
     * 结论：反射非常影响性能，不宜大量使用
     */
    public void performance() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        newObject();
        reflect();
        reflectAccessible();
    }

    public void newObject() {
        long start = System.currentTimeMillis();
        User user = new User(99, "Hank", true, null);
        for (int i = 0; i < LOOP_SIZE; i++) {
            user.getName();
        }
        long end = System.currentTimeMillis();
        log.info("new objet spend {}ms", end - start);
    }

    public void reflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        User user = new User(99, "Hank", true, null);
        Class clazz = user.getClass();
        Method getName = clazz.getDeclaredMethod("getName");
        for (int i = 0; i < LOOP_SIZE; i++) {
            getName.invoke(user);
        }
        long end = System.currentTimeMillis();
        log.info("reflect spend {}ms", end - start);
    }

    public void reflectAccessible() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        User user = new User(99, "Hank", true, null);
        Class clazz = user.getClass();
        Method getName = clazz.getDeclaredMethod("getName");
        getName.setAccessible(true);
        for (int i = 0; i < LOOP_SIZE; i++) {
            getName.invoke(user);
        }
        long end = System.currentTimeMillis();
        log.info("reflect accessible spend {}ms", end - start);
    }
}
