package com.demo.others;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/3/18
 */
@Slf4j
public class TryDemo {
    /**
     * 测试finally
     * 结论：
     * 即使try报错，并且没有catch，finally内的代码也会执行，但finally之后的代码不会执行
     */
    public void test() {
        try {
            int i = 1 / 0;
            log.info("result: {}", i);
        } finally {
            log.info("must be finally");
        }
    }
}
