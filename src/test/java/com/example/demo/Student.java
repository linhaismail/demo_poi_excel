package com.example.demo;

import java.util.Collections;
import java.util.List;

public class Student {
    private String name;
    private int age;
    private List<String> course;

    public Student(String name, int age, List<String> course) {
        this.name = name;
        this.age = age;
        this.course = Collections.unmodifiableList(course);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", course=" + course +
                '}';
    }
}
