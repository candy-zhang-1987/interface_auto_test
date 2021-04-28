package com.lemon.InterfaceAutomation.Day2;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.lemon.interfaceTestCaes.ExcelPojo;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;

public class DataDriverDemo {
    @Test(dataProvider = "getLoginData02")
    public void testAssert(ExcelPojo excelPojo){
        String JsonData=  excelPojo.getInputParams();
        String url= excelPojo.getUrl();
        String requestHander= excelPojo.getRequestHeader();
        Map requestHanderMap = (Map) JSON.parse(requestHander);

        //RestAssured全局配置
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI="http://api.lemonban.com/futureloan";
        //String JsonData="{\"mobile_phone\":\""+mobile_phone+"\",\"pwd\":\""+pwd+"\"}";
        Response res=
                given().
                        body(JsonData).
                        headers(requestHanderMap).
                when().
                        post(url).
                then().log().all().extract().response();
        //断言
        String expectedResult= excelPojo.getExpected();
        Map<String,Object> expectedResultMap=(Map) JSON.parse(expectedResult);
        for(String element:expectedResultMap.keySet()){
            Object expectedValue=   expectedResultMap.get(element);
            //获取实际的返回结果
          Object actualValue=  res.jsonPath().get(element);
            Assert.assertEquals(actualValue,expectedValue);
        }

    }
    @DataProvider
    public Object[][] getLoginData(){
        Object[][] data={{"13323230000","123456"},
                {"1332323111","123456"},
                {"13323230000","12345678"}};
        return data;
    }
    @DataProvider
    public Iterator<Object[]> getLoginData02(){
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
        //导入的参数对象 ImportParams可以设置读取哪个sheet
        ImportParams importParams=new ImportParams();
        importParams.setStartSheetIndex(1);
        //读取Excel
        List<Object> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        List<Object[]> data = new ArrayList<>();
        for (Object object : objectList) {
            //做一个形式转换
            Object[] o={object};
            //users.add(new Object[] { u });
            data.add(o);
        }
        return data.iterator();

    }

    //easyPOI通过映射的思想实现读取，每一列的表头作为属性来映射。
 public static void main(String[] args){
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
        //导入的参数对象 ImportParams可以设置读取哪个sheet
     ImportParams importParams=new ImportParams();
     importParams.setStartSheetIndex(1);
        //读取Excel
     List<Object> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
     for(Object element:objectList){
         System.out.println(element);
     }

 }

}
