package com.demo.jvm;

import com.demo.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author chris
 * @create 2023/4/22
 * <p>
 * 反射
 */
@Slf4j
public class ReflectDemo {
    /**
     * 通过反射获取class对象
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
}
