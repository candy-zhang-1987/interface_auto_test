package com.lemon.testng;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaticProvider {
    @DataProvider (name="create")
    public static Iterator<Object[]> createData() {
        User u1=new User("zhangsan",123456);
        User u2=new User("lisi",123456);
        User u3=new User("wangwu",123456);
        ArrayList<User> userList=new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        List<Object[]> users = new ArrayList<>();
        for (User u : userList) {
            //做一个形式转换
            Object[] o={u};
            //users.add(new Object[] { u });
            users.add(o);
        }
        return users.iterator();
    }
}
