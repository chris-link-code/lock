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
     * 由于一段同步代码一直被同一个线程多次访问，那么该线程在后续访问时便会自动获得锁。
     * 偏向锁会偏向于第一个访问锁的线程，如果在接下来的运行过程中，
     * 该锁没有被其他的线程访问，则持有偏向锁的线程将永远不需要触发同步。
     * 也即偏向锁在资源没有竞争的情况下消除了同步语句，懒的连CAS操作都不做了，直接提高程序性能。
     * <p>
     * 在实际应用运行过程中发现，“锁总是同一个线程持有，很少发生竞争”，
     * 也就是说锁总是被第一个占用他的线程拥有，这个线程就是锁的偏向线程。
     * 那么只需要在锁第一次被拥有的时候，记录下偏向线程ID。
     * 这样偏向线程就一直持有着锁(后续这个线程进入和退出这段加了同步锁的代码块时，
     * 不需要再次加锁和释放锁。而是直接会去检查锁的Mark word里面是不是放的自己的线程ID)。
     * <p>
     * 如果相等，表示偏向锁是偏向于当前线程的，就不需要再尝试获得锁了，直到竞争发生才释放锁。
     * 以后每次同步，检查锁的偏向线程ID与当前线程ID是否一致，如果一致直接进入同步。
     * 无需每次加锁解锁都去CAS更新对象头。如果自始至终使用锁的线程只有一个，
     * 很明显偏向锁几乎没有额外开销，性能极高。
     * <p>
     * 如果不相等，表示发生了竞争，锁已经不是总是偏向于同一个线程了，
     * 这个时候会尝试使用CAS来将Mark Word里面的线程ID替换为新线程的ID。
     * <p>
     * 竞争成功，表示之前的线程不存在了，Mark Word里面的线程ID为新线程ID，
     * 锁不会升级，仍然为偏向锁。
     * <p>
     * 竞争失败，这时候可能需要升级变为轻量级锁，才能保证线程间公平竞争锁。
     * <p>
     * 注意！偏向锁只有遇到其他线程尝试竞争偏向锁时，持有偏向锁的线程才会释放锁，
     * 线程是不会主动释放偏向锁的。
     * <p>
     * 技术实现：
     * 一个synchronized方法被一个线程抢到了锁时，
     * 那这个方法所在的对象就会在其所在的Mark Word中将偏向锁修改状态位，
     * 同时还会有占用前54位来存储线程指针作为标识。
     * 若该线程再次访问同一个synchronized方法时，
     * 该线程只需去对象头的Mark Word中去判断一下是否有偏向锁指向本身的ID，
     * 无需再进入Monitor去竞争对象了。
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
