package com.demo.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/5
 * <p>
 * 代理的优缺点#
 * <p>
 * 优点：
 * 可以提高程序灵活性和可扩展性。
 * 在不修改原代码的基础上，扩展和增强实现；
 * 代码解耦，在代理中通过参数可以判断真实类，做出不同的响应或调用；
 * 隐藏部分实现过程和细节，可以起到保护目标对象的作用
 * <p>
 * 缺点：
 * 由于在客户端和真实对象之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢；
 * 实现代理模式需要额外的工作，有些代理模式的实现非常复杂。
 * 静态代理在委托类变多的情况时会显的非常臃肿，不方便阅读与使用
 * <p>
 * <p>
 * 静态代理
 * <p>
 * 由程序员创建或工具生成代理类的源码，再编译代理类,
 * 即代理类和委托类的关系再程序运行前就已经存在
 * <p>
 * 实现步骤：
 * 定义一个接口和接口的实现类,
 * 创建一个代理类同样的实现上述接口,
 * 将目标对象注入代理类中然后在代理类方法中调用目标类中的对应方法,
 * 这样我们就可以通过代理类在不修改原有方法的基础上进行扩展
 * <p>
 * 优点：在不改变原来的实现类的情况下可以对方法实现了增强
 * 缺点：接口类变化会影响实现类和代理类；
 * 比如方法修改返回值、参数类型、增加方法，实现类和代理类都需要修改，不灵活而且很麻烦。
 */
@Slf4j
public class ChickenStaticProxy implements IStar {
    IStar iStar;

    public ChickenStaticProxy(IStar iStar) {
        this.iStar = iStar;
    }

    /**
     * 不改变Chicken的sing方法
     * 通过ChickenStaticProxy增强sing方法
     *
     * @param song
     */
    @Override
    public void sing(String song) {
        log.info("[静态代理]我是练习时长两年半的个人练习生");
        iStar.sing(song);
        log.info("[静态代理]我还喜欢打篮球");
    }

    @Override
    public void rap(String rap) {
        log.info("我喜欢{}", rap);
    }
}
