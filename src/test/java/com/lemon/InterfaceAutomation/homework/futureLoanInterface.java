package com.lemon.InterfaceAutomation.homework;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class futureLoanInterface {
    String mobile_phone="13212121213";
    String pwd="12345678";
    int type=1;
    int member_id;
    String token;


   // @Test(priority = 1)
    @Test
    public void testRegister(){
        String JsonData="{\"mobile_phone\":\""+mobile_phone+"\",\"pwd\":\""+pwd+"\",\"type\":\""+type+"\"}";
        Response res=
                given().
                        body(JsonData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                when().
                        post("http://api.lemonban.com/futureloan/member/register").
                then().log().all().extract().response();
    }
    //@Test(priority = 2)
    @Test(dependsOnMethods = "testRegister")
    public void testLogin(){
        String JsonData="{\"mobile_phone\":\""+mobile_phone+"\",\"pwd\":\""+pwd+"\"}";
        Response res=
                given().
                        body(JsonData).
                        header("Content-Type","application/json").
                        header("X-Lemonban-Media-Type","lemonban.v2").
                when().
                        post("http://api.lemonban.com/futureloan/member/login").
                then().log().all().extract().response();
        token=res.jsonPath().get("data.token_info.token");
        member_id=res.jsonPath().get("data.id");
        System.out.println(token);
        System.out.println(member_id);


    }
//   // @Test(priority = 3)
    @Test(dependsOnMethods = "testLogin")
    public void testRecharge(){
        String chargeData="{\"member_id\":"+member_id+",\"amount\":10000.00}";
        Response res=
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
