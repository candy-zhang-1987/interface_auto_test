package com.lemon.json;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeWorkJson {

    public static void main(String[] args){
        //1、String json = {"name": "张三", "age": "18", "score":"100"}; 解析成Student对象。
        String json="{\"name\": \"张三\", \"age\": \"18\", \"score\":\"100\"}";
        Student student = JSONObject.parseObject(json, Student.class);
        System.out.println(student);//Student{name='张三', age=18, score=100}
        //2、String json =  [{"name": "张三", "age": "18", "score":"100"},{"name": "李四", "age": "16", "score":"100"}] 解析成List对象。
        String json1="[{\"name\": \"张三\", \"age\": \"18\", \"score\":\"100\"},{\"name\": \"李四\", \"age\": \"16\", \"score\":\"100\"}]";
        List<Student> students = JSONObject.parseArray(json1, Student.class);
        for(Student stu:students){
            System.out.println(stu);  //Student{name='张三', age=18, score=100}  Student{name='李四', age=16, score=100}
        }
        //3、通过遍历map取出每一个班级的学生信息
        HashMap<Integer ,List<StudentInformation>> classStudents=new HashMap<>();
        String json2="[{\"name\": \"张三\", \"age\": 25, \"gender\":\"男\"},{\"name\": \"李四\", \"age\": 26, \"gender\":\"男\"},{\"name\": \"小花\", \"age\": 27, \"gender\":\"女\"}]";
        String json3="[{\"name\": \"小明\", \"age\": 28, \"gender\":\"男\"},{\"name\": \"小红\", \"age\": 29, \"gender\":\"女\"}]";
        List<StudentInformation> st1 = JSONObject.parseArray(json2, StudentInformation.class);
        List<StudentInformation> st2 = JSONObject.parseArray(json3, StudentInformation.class);
        classStudents.put(1801,st1);
        classStudents.put(1802,st2);

        //遍历每一个班级的学生信息，方法一
        for(Integer classNo:classStudents.keySet()){
            List<StudentInformation> stu=classStudents.get(classNo);
            System.out.println(classNo+":"+stu);
        }
        //遍历每一个班级的学生信息，方法2
        for(Map.Entry<Integer, List<StudentInformation>> map:classStudents.entrySet()){
          int  stuClass=map.getKey();
            List<StudentInformation> stuList=  map.getValue();
            System.out.println(stuClass+":"+stuList);
        }
        //4、把下面json字符串解析成java对象
        //{ "name": "中国", "provinces": [{ "name": "黑龙江", "capital": "哈尔滨" }, { "name": "广东", "capital": "广州" }, { "name": "湖南", "capital": "长沙" }] }
        String json4="{ \"name\": \"中国\", \"provinces\": [{ \"name\": \"黑龙江\", \"capital\": \"哈尔滨\" }, { \"name\": \"广东\", \"capital\": \"广州\" }, { \"name\": \"湖南\", \"capital\": \"长沙\" }] }";
        Country country = JSONObject.parseObject(json4, Country.class);
        System.out.println(country);//Country{name='中国', provinces=[Provinces{name='黑龙江', capital='哈尔滨'}, Provinces{name='广东', capital='广州'}, Provinces{name='湖南', capital='长沙'}]}

    }
}
