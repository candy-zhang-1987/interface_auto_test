package com.lemon.InterfaceAutomation.Day1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseDemo {
    @Test
    public void postResponseDemo(){
       Response res= given().
                formParam("mobilephone","13212121212").
                formParam("pwd","123456").
                when().
                post("http://httpbin.org/post").
                then().extract().response();
       System.out.println("接口响应时间"+res.time());
       System.out.println("content-Type"+res.getHeader("content-Type"));
    }
    @Test
    public void getResponseJson(){
        String JsonData="{\"mobile_phone\":\"13212121212\",\"pwd\":\"12345678\"}";
        Response res=
        given().
                body(JsonData).
                header("Content-Type","application/json").
                header("X-Lemonban-Media-Type","lemonban.v1").
        when().
                post("http://api.lemonban.com/futureloan/member/login").
        then().log().all().extract().response();
        String s=res.jsonPath().get("data.reg_name");
        System.out.println(s);
    }
    @Test
    public void getResponseJson02(){
        Response res=
                given().
                when().
                        get("http://www.httpbin.org/json").
                then().log().all().extract().response();
        Object o = res.jsonPath().get("slideshow.slides.title[0]");
        System.out.println(o);
    }
    @Test
    public void getResponseHtml(){
        Response res=
                given().
                when().
                        get("http://www.baidu.com").
                then().log().all().extract().response();
        //获取的是元素
        Object o = res.htmlPath().get("html.head.title");
        System.out.println(o);
        //获取属性的方法如下：
        Object o2 = res.htmlPath().get("html.head.meta[0].@content");
        System.out.println(o2);
    }
    @Test
    public void getResponseXml(){
        Response res=
                given().
                when().
                        get("http://www.httpbin.org/xml").
                then().log().all().extract().response();
        //获取的是元素
       Object o = res.xmlPath().get("slideshow.slide[0].title");
        //获取属性的方法如下：
        Object o2 = res.xmlPath().get("slideshow.slide[0].@type");
        System.out.println(o2);
    }
    @Test
    public void loginRecharge(){
        //login
        String JsonData="{\"mobile_phone\":\"13212121212\",\"pwd\":\"12345678\"}";
        Response res=
                given().
                        body(JsonData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                when().
                        post("http://api.lemonban.com/futureloan/member/login").
                then().log().all().extract().response();
        String token=res.jsonPath().get("data.token_info.token");
        int memberId=res.jsonPath().get("data.id");
        System.out.println(token);
        System.out.println(memberId);
        //recharge
        String chargeData="{\"member_id\":"+memberId+",\"amount\":10000.00}";
        Response res2=
                given().
                        body(chargeData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                        header("Authorization","Bearer "+token).
                when().
                        post("http://api.lemonban.com/futureloan/member/recharge").
                then().log().all().extract().response();

    }
}
