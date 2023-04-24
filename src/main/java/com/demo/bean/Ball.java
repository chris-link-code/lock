package com.demo.bean;

/**
 * @author chris
 * @create 2023/4/24
 */
@Play("db_ball")
public class Ball {
    @Field(column = "db_size", type = "int", length = 16)
    private Integer size;
    @Field(column = "db_weight", type = "int", length = 16)
    private Integer weight;
    @Field(column = "db_name", type = "varchar", length = 32)
    private String name;

    public Ball() {
    }

    public Ball(Integer size, Integer weight, String name) {
        this.size = size;
        this.weight = weight;
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "size=" + size +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }
}
