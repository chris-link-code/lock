package com.demo.bean;

import java.util.List;
import java.util.Map;

/**
 * @author chris
 * @create 2023/2/25
 */
public class Self {
    /**
     * 此方法在gc清理之前执行
     * 一般情况不要重写此方法
     * 此处只作为演示
     *
     * @throws Throwable
     */
    /*@Override
    protected void finalize() throws Throwable {
        System.out.println("invoke finalize()");
    }*/

    /**
     * 测试利用反射获取泛型
     * @param map
     * @param list
     */
    public void getCat(Map<String, Cat> map, List<Cat> list) {
    }
}
