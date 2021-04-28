package com.lemon.interfaceTestCaes;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginTest {


    @BeforeTest
    public void setup(){
        //前置条件
        //RestAssured全局配置
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI="http://api.lemonban.com/futureloan";
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
        List<ExcelPojo> listData= readSpecifyExcelData(file,1,0,1);
        //执行注册接口请求
        String jsonData=listData.get(0).getInputParams();
        String headers=listData.get(0).getRequestHeader();
        Map<String,Object> headerMap=JSON.parseObject(headers);
        String url=listData.get(0).getUrl();
        given().
                body(jsonData).
                headers(headerMap).
        when().
                post(url).
        then().log().all().extract().response();

    }




@Test(dataProvider = "getLoginData")
    public void testLogin(ExcelPojo excelPojo){
        String JsonData=  excelPojo.getInputParams();
        String url= excelPojo.getUrl();
        String requestHander= excelPojo.getRequestHeader();
        Map requestHanderMap = (Map) JSON.parse(requestHander);


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
    public Iterator<Object[]> getLoginData(){
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
//        //导入的参数对象 ImportParams可以设置读取哪个sheet
//        ImportParams importParams=new ImportParams();
//        importParams.setStartSheetIndex(1);
//        //读取Excel
//        List<Object> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        List<ExcelPojo> objectList = readSpecifyExcelData(file,1,1,13);
     List<Object[]> data = new ArrayList<>();
        for (Object object : objectList) {
            //做一个形式转换
            Object[] o={object};
            //users.add(new Object[] { u });
            data.add(o);
        }
        return data.iterator();

    }
    public List<Object> readAllExcelData(File file,int sheetNO){
        //导入的参数对象
        ImportParams importParams=new ImportParams();
        //读取第二个sheet
        importParams.setStartSheetIndex(sheetNO);
        //读取excel的数据
        List<Object> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        return objectList;
    }
    public List<ExcelPojo> readSpecifyExcelData(File file,int sheetNO,int startRow,int rowNumber){
        //导入的参数对象
        ImportParams importParams=new ImportParams();
        //读取第二个sheet
        importParams.setStartSheetIndex(sheetNO);
        //读取指定行
        importParams.setStartRows(startRow);
        //读取多少行
        importParams.setReadRows(rowNumber);
        //读取excel的数据
        List<ExcelPojo> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        return objectList;
    }
}
