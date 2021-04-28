package com.futureloan.testcases;

import com.alibaba.fastjson.JSON;
import com.futureloan.common.BaseMethod;
import com.futureloan.data.Constants;

import com.futureloan.pojo.ExcelPojo;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Recharge extends BaseMethod {
    @BeforeTest
    public void setup(){
        //前置条件
        //RestAssured全局配置
        //json小数返回类型是BigDecimal
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI= Constants.BASE_URI;
        List<ExcelPojo> listData= readSpecifyExcelData(2,0,2);
        //执行注册接口请求
        request(listData.get(0));
        //执行登录接口请求
        Response res=request(listData.get(1));
        String extract=listData.get(1).getExtract();

        extractToEnvironment(extract,res);
        //String member_id=res.jsonPath().get("data.id");
        //String token=res.jsonPath().get("data.token_info.token");

    }
    @Test(dataProvider = "getRechargeData")
    public void testRecharge(ExcelPojo excelPojo){
        //用例执行之前替换{{member_id}} 为环境变量中保存的对应的值
//        String params = regexReplace(excelPojo.getInputParams());
//        System.out.println("替换之前的效果：：" + excelPojo.getInputParams());
//        excelPojo.setInputParams(params);
//        System.out.println("替换之后的效果：：" + excelPojo.getInputParams());
//        //用例执行之前替换{{token}} 为环境变量中保存的对应的值
//        String requestHeader = regexReplace(excelPojo.getRequestHeader());
//        System.out.println("替换之前的效果：：" + excelPojo.getRequestHeader());
//        excelPojo.setRequestHeader(requestHeader);
//        System.out.println("替换之后的效果：：" + excelPojo.getRequestHeader());
        //替换excel来的数据用以上的方式一个个实现，不太方便，所以提取为以下的方法，方便复用。
        replaceCaseData(excelPojo);
        Response res=request(excelPojo);
        //断言
        String expectedResult= excelPojo.getExpected();
        Map<String,Object> expectedResultMap=(Map) JSON.parse(expectedResult);
        for(String element:expectedResultMap.keySet()){
            Object expectedValue=   expectedResultMap.get(element);
            System.out.println(element);
            //获取实际的返回结果
            Object actualValue=  res.jsonPath().get(element);
            Assert.assertEquals(actualValue,expectedValue);
        }

    }
    @DataProvider
    public Iterator<Object[]> getRechargeData(){
        List<ExcelPojo> objectList = readSpecifyExcelData(2,2,1);
        List<Object[]> data = new ArrayList<>();
        for (Object object : objectList) {
            //做一个形式转换
            Object[] o={object};
            //users.add(new Object[] { u });
            data.add(o);
        }
        return data.iterator();

    }
}
