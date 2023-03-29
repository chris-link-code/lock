package com.demo.bean;

import com.demo.extend.Animal;

/**
 * @author chris
 * @create 2023/3/29
 */
public class Cow implements Animal<Grass> {
    @Override
    public void eat(Grass grass) {
        System.out.println("Cow eat " + grass.getName());
    }
}
