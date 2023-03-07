package com.demo.jvm;

import com.demo.bean.Cat;
import com.demo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2023/3/5
 */
@Slf4j
public class ObjectDemo {
    public void test() {
        log.info("VM current details:\r\n{}", VM.current().details());

        Cat cat = new Cat("tom");
        User user = new User(3, "Jerry", true, cat);
        log.info("user memory space:\r\n{}", ClassLayout.parseInstance(user).toPrintable());

        Object object = new Object();
        log.info("object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());

        // hashCode是在调用hashCode()方法后才会产生，存放在对象头里
        int hashCode = object.hashCode();
        log.info("object hashcode: {}", hashCode);
        log.info("object hashcode hex: {}", Integer.toHexString(hashCode));
        log.info("object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());

        // 此时的锁是轻量级锁(thin lock)
        synchronized (object) {
            log.info("synchronized object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());
        }

        // 当线程t_1和t_2争抢锁时，锁会升级为重量级锁(fat lock)
        new Thread(() -> {
            synchronized (object) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("thread 1 synchronized object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());
            }
        }, "t_1").start();

        new Thread(() -> {
            synchronized (object) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("thread 2 synchronized object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());
            }
        }, "t_2").start();
    }
}
