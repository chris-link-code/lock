package com.demo.proxy;

import com.demo.bean.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/8
 */
@Slf4j
public class ReflectDemo {
    public void test() throws ClassNotFoundException {
        /*
         * 获取class的三种方式
         * 1. Class.forName("包名.类名");
         * 2. 类名.class;
         * 3. 对象.getClass();
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
}
