package com.test.jvm;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

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
     * 1.对象头
     * 1.1标记字
     * 用来存储自身运行时的数据，例如：对象的分代年龄、hashCode、锁状态等信息。
     * <p>
     * 1.2类对象指针
     * 用来存储方法区中字节码对象的地址，JVM通过这个指针来确定这个对象是属于那个类的实例。
     * <p>
     * 1.3数组长度
     * 如果对象是数组，则该字段记录数组的长度，如果不是数组，该字段不存在。是一个可选字段。
     * <p>
     * 2.对象体
     * 对象的成员属性，也包括父类的成员属性。
     * <p>
     * 3.对齐字节
     * 对齐字节也叫作填充对齐，其作用是用来保证Java对象所占内存字节数为8的倍数，
     * HotSpot VM的内存管理要求对象起始地址必须是8字节的整数倍
     * <p>
     * 原文链接：https://blog.csdn.net/weixin_45714727/article/details/127418268
     */

    /**
     * 使用openjdk jol工具查看对象内存布局
     */
    public void test() {
        Object object = new Object();
        log.info("object memory space:\r\n{}", ClassLayout.parseInstance(object).toPrintable());
    }
}
