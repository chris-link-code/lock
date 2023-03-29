package com.demo.extend;

import com.demo.bean.Cow;
import com.demo.bean.Grass;

/**
 * @author chris
 * @create 2023/3/29
 */
public class ImplDemo {
    public void test() {
        Animal cow = new Cow();
        cow.eat(new Grass(1, "bamboo"));
    }
}
