package com.lemon.HomeWork;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class HomeWorkDay9Section2 {
    public static void main(String[] args){
        String s1="[{\"value\":0,\"expression\":\"$.code\"},{\"value\":\"OK\",\"expression\":\"$.msg\"},{\"value\":\"OK\",\"expression\":\"OK\"}]";
        String s2="[{\"value\":\"OK\",\"expression\":\"OK\"},{\"value\":\"OK\",\"expression\":\"OK\"},{\"value\":\"OK\",\"expression\":\"OK\"}]";
        List<JsonValidate> jv1 = JSONObject.parseArray(s1, JsonValidate.class);
        List<JsonValidate> jv2 = JSONObject.parseArray(s2, JsonValidate.class);
        for(JsonValidate element:jv1){
          String value=element.getValue();
          String expression=element.getExpression();
         if(value.equals(expression)){
             System.out.println("pass");
         }else{
             System.out.println("fail");
         }
        }

    }
}
