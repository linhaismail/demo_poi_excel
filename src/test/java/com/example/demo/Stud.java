package com.example.demo;

import lombok.Data;

@Data
public class Stud {
    public Stud(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String name;
    int age;
}
