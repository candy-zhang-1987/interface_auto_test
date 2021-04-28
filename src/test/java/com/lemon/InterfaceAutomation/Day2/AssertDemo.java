package com.lemon.InterfaceAutomation.Day2;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class AssertDemo {
    @Test
    public void testAssert(){
        //RestAssured全局配置
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI="http://api.lemonban.com/futureloan";
        String JsonData="{\"mobile_phone\":\"13212121212\",\"pwd\":\"12345678\"}";
        Response res=
                given().
                        body(JsonData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        when().
                        post("/member/login").
                        then().log().all().extract().response();
        //1、响应结果断言
        int code=res.jsonPath().get("code");
        Assert.assertEquals(code,0);
        String msg=res.jsonPath().get("msg");
        Assert.assertEquals(msg,"OK");
        BigDecimal leave_amount=res.jsonPath().get("data.leave_amount");
       // Assert.assertEquals(leave_amount,10000.11);//java.lang.AssertionError: expected [10000.11] but found [10000.11]
        //java.lang.AssertionError: expected [10000.11] but found [10000.1103515625]
        //以上断言出错是因为：restAssured返回json的小数是float类型，会导致精度丢失的问题。
        // 解决办法是声明RestAssured的Json返回的小数位BigDecimal格式,加上以下
        //config(RestAssured.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))).
        //以上问题的原因是类型不匹配
        BigDecimal expectedValue=BigDecimal.valueOf(10000.11);
        Assert.assertEquals(leave_amount,expectedValue);
        //2、数据库断言

    }
}
