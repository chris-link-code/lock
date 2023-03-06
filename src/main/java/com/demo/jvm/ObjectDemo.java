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

    /**
     * Java的线程是映射到操作系统原生线程之上的，如果要阻塞或唤醒一个线程就需要操作系统介入，
     * 需要在户态与内核态之间切换，这种切换会消耗大量的系统资源，
     * 因为用户态与内核态都有各自专用的内存空间，专用的寄存器等，
     * 用户态切换至内核态需要传递给许多变量、参数给内核，
     * 内核也需要保护好用户态在切换时的一些寄存器值、变量等，
     * 以便内核态调用结束后切换回用户态继续工作。
     * <p>
     * 在Java早期版本中，synchronized属于重量级锁，效率低下，
     * 因为监视器锁(monitor)是依赖于底层的操作系统的Mutex Lock(系统互斥量)来实现的，
     * 挂起线程和恢复线程都需要转入内核态去完成，
     * 阻塞或唤醒一个Java线程需要操作系统切换CPU状态来完成，这种状念切换需要耗费处理器时间，
     * 如果同步代码块中内容过于简单，这种切换的时问可能比用户代码执行的时间还长”，
     * 时间成本相对较高，这也是为什么早期的synchronized效率低的原因。
     * Java 6之后，为了减少获得锁和释放锁所带来的性能消耗，引入了轻量级锁和偏向锁。
     */

    /**
     * 1. 偏向锁：单线程竞争
     * 当线程A第一次竞钟到锁时，通过操作修改Mark word中的偏向线程ID、偏向模式。
     * 偏向锁会偏向于第一个访问锁的线程，如果在接下来的运行过程中，
     * 该锁没有被其他的线程访问，则持有偏向锁的线程将永远不需要触发同步。
     * 也即偏向锁在资源没有竞争的情况下消除了同步语句，懒的连CAS操作都不做了，直接提高程序性能
     */
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
