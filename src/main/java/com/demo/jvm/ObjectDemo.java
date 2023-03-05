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
    /**
     * Java对象的内存结构
     * Java对象的内存结构分为对象头、对象体和对齐字节。
     * <p>
     * 1.对象头(header)
     * 1.1标记字(mark word)
     * 用来存储自身运行时的数据，例如：对象的分代年龄、hashCode、锁状态等信息。
     * <p>
     * 1.2类型指针(class pointer)
     * 用来存储方法区中字节码对象的地址，JVM通过这个指针来确定这个对象是属于那个类的实例。
     * <p>
     * 1.3数组长度(length)
     * 如果对象是数组，则该字段记录数组的长度，如果不是数组，该字段不存在。是一个可选字段。
     * <p>
     * 2.对象体(instance data)
     * 对象的成员属性，也包括父类的成员属性。
     * <p>
     * 3.对齐字节(padding)
     * 对齐字节也叫作填充对齐，其作用是用来保证Java对象所占内存字节数为8的倍数，
     * HotSpot VM的内存管理要求对象起始地址必须是8字节的整数倍
     * <p>
     * 原文链接：https://blog.csdn.net/weixin_45714727/article/details/127418268
     */

    /**
     * 参考周志明《深入理解JVM虚拟机》2.3.2 对象的内存布局
     * 使用openjdk jol工具查看对象内存布局
     * <p>
     * OFFSET：偏移地址，单位为字节;
     * SIZE：占用内存大小，单位为字节;
     * TYPE：Class中定义的类型;
     * DESCRIPTION：类型描述，object header表示对象头，object alignment表示对齐填充;
     * VALUE：对应内存中存储的值;
     * 原文链接：https://blog.csdn.net/uuqaz/article/details/123340729
     */
    public void test() {
        log.info("VM current details:\r\n{}", VM.current().details());

        Cat cat = new Cat("tom");
        User user = new User(3, "Jerry", true, cat);
        log.info("user memory space:\r\n{}", ClassLayout.parseInstance(user).toPrintable());

        Object object = new Object();
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
