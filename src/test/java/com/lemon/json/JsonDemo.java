package com.lemon.json;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

public class JsonDemo {
    public static void main(String[] args){
        String jsonStr="{\"name\": \"张三\", \"age\": 18, \"score\":100}";
        //1json字符串转JAVA对象,JSONObject.parseObject(JSON字符串，JAVA对象)
        Student student = JSONObject.parseObject(jsonStr, Student.class);
        System.out.println(student);//Student{name='张三', age=18, score=100}
        //2java对象转json对象
        String s = JSONObject.toJSONString(student);
        //以下输出对比能发现：JSON的属性没有先后顺序
        System.out.println(s);//{"age":18,"name":"张三","score":100}
        System.out.println(jsonStr);//{"name": "张三", "age": 18, "score":100}
        //JSON--MAP可以互转
        HashMap<String,Object> hashMap = JSONObject.parseObject(jsonStr, HashMap.class);
        System.out.println(hashMap);//{score=100, name=张三, age=18}
        HashMap<String,Object> hashMap2 =new HashMap<>();
        hashMap2.put("code",0);
        hashMap2.put("message","login fail");
        String s1 = JSONObject.toJSONString(hashMap2);
        System.out.println(s1);//{"code":0,"message":"login fail"}
        //3JSON字符串转JAVA数组
        String jsonStr2="[{\"name\": \"张三\", \"age\": 18},{\"name\": \"李四\", \"age\": 16}]";
        List<Student> students = JSONObject.parseArray(jsonStr2, Student.class);
        for(Student stu:students){
            System.out.println(stu);//Student{name='张三', age=18, score=0}
                                    // Student{name='李四', age=16, score=0}
        }
    }
}
