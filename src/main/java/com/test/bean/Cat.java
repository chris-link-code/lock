package com.test.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author chris
 * @create 2022/7/31
 */
@Slf4j
public class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public int getAge() {
        int age = 0;
        try {
            TimeUnit.SECONDS.sleep(1);
            age = new Random().nextInt(10);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return age;
    }

    public String getName() {
        return name;
    }
}
