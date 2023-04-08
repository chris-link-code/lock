package com.demo.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chris
 * @create 2023/4/5
 * <p>
 * 动态代理
 * 使用反射和字节码的技术，在运行期创建指定接口或类的子类，以及其实例对象，
 * 控制被代理对象的访问，使用的工具有 jdk proxy、cglib proxy 等。
 * 静态代理在程序运行前就需要把对应的类和方法写好，这样就被写死了，
 * 这只是一个简单的接口里面也只有两个方法，那如果接口中有几十个方法都需要扩展呢，
 * 总不能一个个地手动去添加吧，所以有了我们的动态代理
 * <p>
 * 实现步骤：
 * 定义一个接口和接口的实现类，
 * 创建一个代理类实现InvocationHandler接口，
 * 指定运行时生成代理类需要完成的具体任务，
 * 重写InvocationHandler接口中的invoke方法，
 * 创建被代理类的对象，调用处理程序最后通过代理类对象来调用对应方法
 */
@Slf4j
public class ChickenDynamicProxy implements InvocationHandler {
    IStar iStar;

    public ChickenDynamicProxy(IStar iStar) {
        this.iStar = iStar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("[动态代理]我是练习时长两年半的个人练习生");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                log.info("args[{}]: {}", i, args[i].toString());
            }
        }
        Object invoke = method.invoke(iStar, args);
        // 代理实现多个方法
        if ("sing".equals(method.getName())) {
            log.info("[动态代理]我喜欢唱");
        }
        if ("rap".equals(method.getName())) {
            log.info("[动态代理]我喜欢rap");
        }
        return invoke;
    }
}
