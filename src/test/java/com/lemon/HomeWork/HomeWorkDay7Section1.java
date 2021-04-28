package com.lemon.HomeWork;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeWorkDay7Section1 {
    public static void main(String[] args){
            /*
    一、新建一个类ArrayListDemo，使用今天上课讲解api完成下面需求。

1、定义老师类Teacher，私有属性：name，age，提供空参有参构造，提供get/set方法，重写toString()。

2、创建老师对象t1，name：张三，age：25

3、创建老师对象t2，name：李四，age：35

4、创建老师对象t3，name：老王，age：19

5、创建老师对象t4，name：赵六，age：29

6、创建ArrayList集合对象存储t1，t2，t3，t4

7、通过普通for循环和增强for循环打印所有老师数据。打印出对应的name和age属性。

8、请求出集合中的老师平均年龄。
     */
        Teacher t1=new Teacher("张三",25);
        Teacher t2=new Teacher("李四",35);
        Teacher t3=new Teacher("老王",19);
        Teacher t4=new Teacher("赵六",29);
        ArrayList<Teacher> al=new ArrayList();
        al.add(t1);
        al.add(t2);
        al.add(t3);
        al.add(t4);
        for(int i=0;i<al.size();i++){
            System.out.println(al.get(i).getName()+":"+al.get(i).getAge());
        }
        for(Teacher element:al){
            System.out.println(element.getName()+":"+element.getAge());
        }
        int ageSum=0;
        for(int i=0;i<al.size();i++){
            ageSum+=al.get(i).getAge();
        }
        double avgAge=ageSum/al.size();
        System.out.println("老师的平均年龄是"+avgAge);
        //二、有字符串String s = "abc,123,中国,llllll,haha"; 最终输出：abc：3，123：3，中国：2，llllll:6，haha:4 数字代表长度
        String s = "abc,123,中国,llllll,haha";
        String[] split = s.split(",");
        for(int i=0;i< split.length;i++){
            System.out.print(split[i]+":"+split[i].length()+",");
        }
        //三、翻转字符串 aiwozhonghua ，结果auhgnohzowia
        String s1 ="aiwozhonghua";
        String[] split1 = s1.split("");
        for(int i=0;i< split1.length;i++){
            for(int j=0;j<split1.length-i-1;j++){
                String temp=split1[j];
                split1[j]=split1[j+1];
                split1[j+1]=temp;
            }
        }
        //数组的字符串拼接是否有别的方法？
        for(int i=0;i< split1.length;i++){
            System.out.print(split1[i]);
        }
        System.out.println();
        /*
        四、有HashMap对象{"id":"1","mobile_phone":"13212312312","pwd":"12312312"}

a、判断Map中是否有mobile_phone 键，如果有输出对应的值，如果没有提示没有。(提示：查询api文档中HashMap中判断是否存在键的方法。)

b、替换Map中pwd对应的值，替换为88888888。
         */
        HashMap<String,String> map=new HashMap();
        map.put("id", "1");
        map.put("mobile_phone","13212312312");
        map.put("pwd","12312312");
        boolean phoneKey = map.containsKey("mobile_phone");
        if(phoneKey){
            //map.get("mobile_phone");
            System.out.println(map.get("mobile_phone"));
        }else{
            System.out.println("没有该键");
        }
        if(map.containsKey("pwd")){
            map.put("pwd","88888888");
            System.out.println(map.get("pwd"));
        }
    }
}
