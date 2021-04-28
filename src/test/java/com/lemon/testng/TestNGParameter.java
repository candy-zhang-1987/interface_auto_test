package com.lemon.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNGParameter {
    //参数化，或者是数据驱动
    @Test
    @Parameters({"type","sheetIndex"})
    public void f(String type,int index){
        System.out.println("type = " + type + ", index = " + index);
    }
}
