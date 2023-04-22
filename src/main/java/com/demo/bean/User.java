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
    private Integer age;
    private String name;
    private Boolean gender;
    private Cat cat;
}
