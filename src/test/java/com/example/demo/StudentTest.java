package com.example.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class StudentTest {

    @Test
    public void test1(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("English");
        Student s = new Student("小强", 14, arrayList);

        List<String> course = s.getCourse();
        course.add("Math");

        System.out.println(s);
    }
}
