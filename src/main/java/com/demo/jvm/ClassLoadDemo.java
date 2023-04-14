package com.demo.jvm;

import com.demo.bean.Shark;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/14
 * <p>
 * 类加载测试
 */
@Slf4j
public class ClassLoadDemo {
    static {
        log.info("ClassLoadDemo 类被加载");
    }

    public void test() {
        Shark shark = new Shark();
    }
}
