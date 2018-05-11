package com.example.demo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transform {

    public static<T> List<KeyNameValueComparison> getJsonList(T t, Map<String, String> keyNameMap){
        ArrayList<KeyNameValueComparison> list = new ArrayList<>();
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            KeyNameValueComparison keyNameValue = new KeyNameValueComparison();
            String key = f.getName();
            String name = keyNameMap.get(key);
            Object value = null;
            try {
                value = f.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            keyNameValue.setKey(key);
            if (name != null){
                keyNameValue.setName(name);
            }
            if (value != null){
                keyNameValue.setValue(value.toString());
            }

            list.add(keyNameValue);
        }
        return list;
    }

    private static final Map<String, String> m = new HashMap<>();
    static{
        m.put("name", "姓名");
        m.put("age", "年龄");
    }

    public static void main(String[] args) {

        Stud s = new Stud("旺财", 20);

        List<KeyNameValueComparison> jsonList = getJsonList(s, m);

        System.out.println("结果>>>>>>:"+jsonList);
    }
}
