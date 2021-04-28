package com.lemon.interfaceTestCaes;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class RechargeTest {
    @BeforeTest
    public void setup(){
        //前置条件
        //RestAssured全局配置
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI="http://api.lemonban.com/futureloan";
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
        List<ExcelPojo> listData= readSpecifyExcelData(file,2,0,2);
        //执行注册接口请求
        String jsonData=listData.get(0).getInputParams();
        String headers=listData.get(0).getRequestHeader();
        Map<String,Object> headerMap= JSON.parseObject(headers);
        String url=listData.get(0).getUrl();
        Response res=given().
                body(jsonData).
                headers(headerMap).
                when().
                post(url).
                then().log().all().extract().response();
        int memberId=res.jsonPath().get("data.id");
        Environment.memberId=memberId;
        System.out.println(memberId);
        //执行登录请求
        String headers02=listData.get(1).getRequestHeader();
        Map<String,Object> headerMap02= JSON.parseObject(headers);
        Response res2= given().
                body(listData.get(1).getInputParams()).
                headers(headerMap02).
                when().
                post(listData.get(1).getUrl()).
                then().log().all().extract().response();
        String token=res2.jsonPath().get("data.token_info.token");
        Environment.token=token;
        System.out.println(token);

    }




    @Test(dataProvider = "getRechargeData")
    public void testRecharge(ExcelPojo excelPojo){
        String JsonData= replace(excelPojo.getInputParams(),Environment.memberId+"") ;
        System.out.println(JsonData);
        String url= excelPojo.getUrl();
        String requestHander= replace(excelPojo.getRequestHeader(),Environment.token);
        Map requestHanderMap = (Map) JSON.parse(requestHander);
        System.out.println(requestHander);

        //String JsonData="{\"mobile_phone\":\""+mobile_phone+"\",\"pwd\":\""+pwd+"\"}";
        Response res=
                given().
                        body(JsonData).
                        headers(requestHanderMap).
                        when().
                        post(url).
                        then().log().all().extract().response();
//        //断言
//        String expectedResult= excelPojo.getExpected();
//        Map<String,Object> expectedResultMap=(Map) JSON.parse(expectedResult);
//        for(String element:expectedResultMap.keySet()){
//            Object expectedValue=   expectedResultMap.get(element);
//            //获取实际的返回结果
//            Object actualValue=  res.jsonPath().get(element);
//            Assert.assertEquals(actualValue,expectedValue);
//        }

    }
    @DataProvider
    public Iterator<Object[]> getRechargeData(){
        File file=new File("D:\\Candy\\Code\\JavaBase_Maven\\src\\test\\resources\\api_testcases_futureloan_v1.xls");
        List<ExcelPojo> objectList = readSpecifyExcelData(file,2,2,1);
        List<Object[]> data = new ArrayList<>();
        for (Object object : objectList) {
            //做一个形式转换
            Object[] o={object};
            //users.add(new Object[] { u });
            data.add(o);
        }
        return data.iterator();

    }
    public String replace(String originalStr,String replace){
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher = pattern.matcher(originalStr);
        while(matcher.find()){
            //group(0)获取到整个匹配的内容
            String outStr=matcher.group(0);
            //System.out.println(outStr);
            //group(1)表示获取{{}}包着的内容
            String inStr=matcher.group(1);
            //System.out.println(inStr);
            //replace
            originalStr=originalStr.replace(outStr,replace);
            System.out.println(originalStr);
        }
        return originalStr;
    }
    public static void main(String[] args){
        //正则表达式测试
        String str="einfnjwiddn{{memberId}}dhnfnfn{{token}}\n"+
                "dcfgghnn{{phone}}";
        int memeberId=1111;
        String token="qddffrrr";
        String phone="18100001234";
        String result;
        //pattern:正则表达式的匹配器
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        //matcher:去匹配一个原始字符串，得到匹配对象
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            //group(0)获取到整个匹配的内容
            String outStr=matcher.group(0);
            //System.out.println(outStr);
            //group(1)表示获取{{}}包着的内容
            String inStr=matcher.group(1);
            //System.out.println(inStr);
            //replace
            str=str.replace(outStr,inStr);
            System.out.println(str);
        }


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
    public void response(ExcelPojo excelPojo){
        String url=excelPojo.getUrl();
        String method=excelPojo.getMethod();
        String headers=excelPojo.getRequestHeader();
        String params=excelPojo.getInputParams();
       Map<String,Object> headersMap = (Map) JSON.parse(headers);
       //对get,post,patch,put做封装
        if("get".equalsIgnoreCase(method)){

        }
    }
}
