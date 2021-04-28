package com.lemon.HomeWork;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class HomeWorkDay11 {

    public void register(String username,String password,String type,String sex){
        System.out.println("username = " + username + ", password = " + password + ", type = " + type + ", sex = " + sex);
    }
    @Test(dataProvider = "datas")
    public void register2(Student stu){
        System.out.println("stu = " + stu);
    }
    @DataProvider
    public Iterator<Object[]> datas(){
        Student s1=new Student("zhangsan","123456","一班学生","male");
        Student s2=new Student("lisi","123457","一班学生","female");
        Student s3=new Student("wangwu","123457","一班学生","female");
        //Object[] stu={s1,s2,s3};
        ArrayList<Object> al=new ArrayList<>();
        al.add(s1);
        al.add(s2);
        al.add(s3);
        ArrayList<Object[]> al2=new ArrayList<>();
        for(Object element:al){
            Object[] o={element};
            al2.add(o);
        }
        Iterator<Object[]> iterator = al2.iterator();
        return iterator;
    }








    @Test
    public void f(){
        String s="AAAbcccccc";
        HashMap<Character, Integer> hm=new HashMap<>();

        char[] chars1 = s.toCharArray();
        for(char element:chars1){

            if(hm.keySet().contains(element)){
                Integer value = hm.get(element);
                value++;
                hm.put(element,value);

            }else{
                hm.put(element,1);
            }

        }
        System.out.println(hm);
        for(Character key:hm.keySet()){
            Integer integer = hm.get(key);
            System.out.print(key+"("+integer+")");
        }

    }

    }

