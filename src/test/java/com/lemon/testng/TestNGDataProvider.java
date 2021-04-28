package com.lemon.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNGDataProvider {
    //@Test(dataProvider = "datas")
    @Test(dataProvider = "create",dataProviderClass = StaticProvider.class)
    public void f(User user){
        System.out.println("name = " + user.getUserName() + ", password = " + user.getPassword());
    }
    @Test(dataProvider = "datas")
    public void f2(String name,String password){
        System.out.println("name = " + name + ", password = " + password);
    }
    @DataProvider
    public Object[][] datas(){
        Object[][] data={{"zhangsan","123456"},{"lisi","123456"},{"wangwu","123456"}};
        return data;
    }

}
