package com.futureloan.testcases;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.futureloan.common.BaseMethod;
import com.futureloan.data.Constants;
import com.futureloan.data.Environment;
import com.futureloan.pojo.ExcelPojo;
import com.futureloan.util.PhoneRandomUtil;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.config.LogConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login extends BaseMethod {

    @BeforeClass
    public void setup(){
        //前置条件

        List<ExcelPojo> listData= readSpecifyExcelData(1,0,1);
        String phone = PhoneRandomUtil.phoneRandomgenerate();
        Environment.envData.put("phone",phone);
        replaceCaseData(listData.get(0));
        //执行注册接口请求
        request(listData.get(0));
    }
    @Test(dataProvider = "getLoginData")
    public void testLogin(ExcelPojo excelPojo) throws FileNotFoundException {
        //为每一次请求做日志保存
        String logFilePath=System.getProperty("user.dir")+"\\log\\test"+excelPojo.getCaseId()+".log";
        PrintStream fileOutPutStream = new PrintStream(new File(logFilePath));
        RestAssured.config =
                RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));

        replaceCaseData(excelPojo);
        Response res=request(excelPojo);
        //向Allure中添加日志
        Allure.addAttachment("接口请求响应信息",new FileInputStream(logFilePath));
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
    public Iterator<Object[]> getLoginData(){
        List<ExcelPojo> objectList = readSpecifyExcelData(1,1,13);
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
