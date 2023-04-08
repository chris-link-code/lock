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
    public void sing(String song) {
        log.info("演唱歌曲{}", song);
    }

    @Override
    public void rap(String rap) {
        log.info("说{}", rap);
    }
}
