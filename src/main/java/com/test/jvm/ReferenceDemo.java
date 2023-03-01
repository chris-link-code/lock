package com.test.jvm;

import com.test.bean.Self;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2023/2/25
 */
@Slf4j
public class ReferenceDemo {
    /**
     * 强引用
     */
    public void strongReference() {
        Self self = new Self();
        log.info("gc before: {}", self);
        // 将引用置为为null后，gc时就会被清理
        self = null;
        // 手动开启gc，一般情况不要使用
        System.gc();
        log.info("gc after: {}", self);
    }

    /**
     * 使用场景：
     * 假如有一个应用需要读取大量的本地图片；
     * 如果每次读取图片都从硬盘读取则会严重影响性能；
     * 如果一次性全部加载到内存中又可能造成内存溢出；
     * 此时使用软引用可以解决这个问题；
     * 设计思路是：用一个HashMap来保存图片的路径和相应图片对象关联的软引用之间的映射关系；
     * 在内存不足时，JM会自动回收这些缓存图片对象所占用的空间，从而有效地避免了OOM的问题。
     * Map<String, SofReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
     */

    /**
     * 软引用;
     * 当系统内存充足时，gc不会回收;
     * 当系统内存不充足时，gc会回收
     */
    public void softReference() {
        SoftReference<Self> self = new SoftReference<>(new Self());
        log.info("soft reference: {}", self.get());

        // 启动参数设为 -Xms1m -Xmx10m，最大内存限定为10MB
        // 手动开启gc，一般情况不要使用
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("gc after memory free: {}", self.get());
        }

        try {
            // 占用20MB
            byte[] bytes = new byte[20 * 1024 * 1024];
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("gc after memory full: {}", self.get());
        }
    }

    /**
     * 弱引用；
     * 无论系统内存是否够用；
     * 只要gc，就一定会被回收；
     */
    public void weakReference() {
        WeakReference<Self> self = new WeakReference<>(new Self());
        log.info("gc before: {}", self);
        // 手动开启gc，一般情况不要使用
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("gc after memory free: {}", self.get());
        }
    }

    /**
     * 1 虚引用必须和引用队列 (ReferenceQueue)联合使用
     * 虛引用需要java.lang.ref.PhantomReference类来实现,顾名思义，就是形同虚设，
     * 与其他几种引用都不同，虚引用并不会决定对象的生命周期。
     * 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收，
     * 它不能单独使用也不能通过它访问对象，虚引用必须和引用队列 (ReferenceQueue)联合使用。
     * <p>
     * 2 PhantomReference的get方法总是返回null
     * 虛引用的主要作用是跟踪对象被垃圾回收的状态。
     * 仅仅是提供了一和确保对象被finalize以后，做某些事情的通知机制。
     * PhantomReference的get方法总是返回null，因此无法访问对应的引用对象。
     * <p>
     * 3 处理监控通知使用
     * 换句话说，设置虛引用关联对象的唯一目的，
     * 就是在这个对象被收集器回收的时候收到一个系统通知或者后续添加进一步的处理，
     * 用来实现比finalize机制更灵活的回收操作
     */
    public void phantomReference() {
        Self self = new Self();
        ReferenceQueue<Self> queue = new ReferenceQueue<>();
        PhantomReference<Self> reference = new PhantomReference<>(self, queue);
        log.info("reference.get(): {}", reference.get());

        List<byte[]> list = new ArrayList<>();
        new Thread(() -> {
            while (true) {
                // 不断存入1MB
                list.add(new byte[1 * 1024 * 1024]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("list add 1MB,reference.get(): {}", reference.get());
            }
        }, "t_1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends Self> poll = queue.poll();
                if (poll != null) {
                    log.info("有虚引用回收被加入了队列");
                    break;
                }
            }
        }, "t_2").start();
    }
}
