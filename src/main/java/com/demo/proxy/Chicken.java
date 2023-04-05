package com.demo.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chris
 * @create 2023/4/5
 * <p>
 * 想要代理的目标类
 */
@Slf4j
public class Chicken implements IStar {
    @Override
    public String sing(String song) {
        log.info("鸡哥演唱歌曲{}", song);
        return "哎呦，你干嘛";
    }

    public void dance(String dance) {
        log.info("鸡哥跳舞{}", dance);
    }
}
