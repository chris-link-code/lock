package com.demo.bean;

import com.demo.extend.Animal;

/**
 * @author chris
 * @create 2023/3/29
 */
public class Dog implements Animal<String> {
    @Override
    public void eat(String food) {
        System.out.println("Dog eat " + food);
    }
}
