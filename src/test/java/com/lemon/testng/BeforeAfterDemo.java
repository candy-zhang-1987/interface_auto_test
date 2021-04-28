package com.lemon.testng;

import org.testng.annotations.*;

public class BeforeAfterDemo {
    @Test
    public void test(){
        //...前置条件 注册。。鉴权。。
        System.out.println("用户登录业务测试1.。。。");
        //...后置条件 数据清理 日志记录。。。。
    }
    @Test
    public void test2(){
        System.out.println("用户登录业务测试2.。。。");
    }
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("BeforeAfterDemo.beforeSuite");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("BeforeAfterDemo.afterSuite");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("BeforeAfterDemo.beforeTest");
    }
    @AfterTest
    public void afterTest(){
        System.out.println("BeforeAfterDemo.afterTest");
    }
    @BeforeClass
    public void beforeClass(){
        System.out.println("BeforeAfterDemo.beforeClass");
    }
    @AfterClass
    public void afterClass(){
        System.out.println("BeforeAfterDemo.afterClass");
    }
    @BeforeMethod
    public void beforeMethod(){
        System.out.println("BeforeAfterDemo.beforeMethod");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("BeforeAfterDemo.afterMethod");
    }
}
