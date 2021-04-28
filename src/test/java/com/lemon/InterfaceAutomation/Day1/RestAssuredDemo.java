package com.lemon.InterfaceAutomation.Day1;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class RestAssuredDemo {
    @Test
    public void firstDemo(){
        //链式调用
        given().
                //设置测试预设（包括请求头、请求参数、请求体、cookies 等等）

        when().//所要执行的操作（GET/POST 请求）
                get("https://www.baidu.com").

        then().
                log().body();// 解析结果、断言
    }
    @Test
    public void getDemo1(){

        given().
        when().
                get("http://httpbin.org/get?mobilephone=13212121212&pwd=123456").
        then().
                log().body();
    }
    @Test
    public void getDemo2(){

        given().
                queryParam("mobilephone","13212121212").
                queryParam("pwd","123456").
        when().

                get("http://httpbin.org/get").
                then().
                log().body();
    }
    @Test
    public void postFormDemo(){
        given().
                formParam("mobilephone","13212121212").
                formParam("pwd","123456").
                contentType("application/x-www-form-urlencoded").
                when().
                post("http://httpbin.org/post").
                then().
                log().all();
    }
    @Test
    public void postJsonDemo(){
        String jsonData="{\"mobile_phone\":\"13212121212\",\"pwd\":\"12345678\"}";
        given().
                body(jsonData).
                contentType("application/json").
        when().
                post("http://httpbin.org/post").
        then().
                log().all();
    }
    @Test
    public void postXmlDemo(){
        String xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<suite name=\"All Test Suite\">\n" +
                "<test name=\"模块/接口A\">\n" +
                "<classes>\n" +
                "<class name=\"com.lemon.testng.TestNGDemo2\"/>\n" +
                "</classes>\n" +
                "</test>\n" +
                "</suite>\n";
        given().
                body(xmlData).contentType(ContentType.XML).
                header("content-Type","application/xml;charset=utf-8").
                when().
                post("http://httpbin.org/post").
        then().
                log().all();
    }
    @Test
    public void postMultipartDemo(){
        File file = new File("D:\\Candy\\day01.txt");
        given().
                multiPart(file).
        when().
                post("http://httpbin.org/post").
        then().
                log().all();
    }

}
