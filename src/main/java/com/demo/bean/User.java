package com.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chris
 * @create 2023/3/5
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int age;
    private String name;
    private boolean gender;
    private Cat cat;
}