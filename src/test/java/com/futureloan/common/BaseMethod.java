package com.futureloan.common;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.futureloan.data.Constants;
import com.futureloan.data.Environment;
import com.futureloan.pojo.ExcelPojo;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseMethod {

    @BeforeTest
    public void globalSetup() throws FileNotFoundException {
        //RestAssured全局配置
        //json小数返回类型是BigDecimal
        RestAssured.config=RestAssured.config.jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        //配置BaseURL全局配置
        RestAssured.baseURI= Constants.BASE_URI;
        //全局重定向把日志文件输出到指定文件中(通过REST-Assured过滤器实现)
        File file=new File(System.getProperty("user.dir")+"\\log");
        if(!file.exists()){
            file.mkdir();
        }
        PrintStream fileOutPutStream = new PrintStream(new File("log\\test_all.log"));
        RestAssured.filters(new RequestLoggingFilter(fileOutPutStream),new ResponseLoggingFilter(fileOutPutStream));
    }
    public Response request(ExcelPojo excelPojo){
        String params=  excelPojo.getInputParams();
        String url= excelPojo.getUrl();
        String requestHander= excelPojo.getRequestHeader();
        Map requestHanderMap = (Map) JSON.parse(requestHander);
        Response res=null;
        if("get".equalsIgnoreCase(excelPojo.getMethod())){
        res= given().headers(requestHanderMap).
                when().get(url).then().log().all().extract().response();}
        else if("post".equalsIgnoreCase(excelPojo.getMethod())){
            res= given().body(params).headers(requestHanderMap).
                    when().post(url).then().log().all().extract().response();
        }else if ("patch".equalsIgnoreCase(excelPojo.getMethod())){
            res= given().body(params).headers(requestHanderMap).
                    when().patch(url).then().log().all().extract().response();
        }
        return res;
    }
    public List<Object> readAllExcelData( int sheetNO){
        File file=new File(Constants.EXCEL_FILE_PATH);
        //导入的参数对象
        ImportParams importParams=new ImportParams();
        //读取第二个sheet
        importParams.setStartSheetIndex(sheetNO);
        //读取excel的数据
        List<Object> objectList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        return objectList;
    }
    public List<ExcelPojo> readSpecifyExcelData(int sheetNO,int startRow,int rowNumber){
        File file=new File(Constants.EXCEL_FILE_PATH);
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
    public void extractToEnvironment(String extract,Response res){
        Map<String,Object> extractMap =  JSONObject.parseObject(extract, Map.class);
        //{"member_id":"data.id","token":"data.token_info.token"}
        //循环遍历extractMap
        for (String key : extractMap.keySet()){
            Object path = extractMap.get(key);
            //根据【提取返回数据】里面的路径表达式去提取实际接口对应返回字段的值
            Object value = res.jsonPath().get(path.toString());
            //存到环境变量中
            Environment.envData.put(key,value);
        }
        System.out.println(Environment.envData);
    }
    public String regexReplace(String originalStr){
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
            Object replaceStr = Environment.envData.get(inStr);
            originalStr=originalStr.replace(outStr,replaceStr.toString());
            System.out.println(originalStr);
        }
        return originalStr;
    }
    public void replaceCaseData(ExcelPojo excelPojo){
        //用例执行之前替换{{member_id}} 为环境变量中保存的对应的值
        String params = regexReplace(excelPojo.getInputParams());
        System.out.println("替换之前的效果：：" + excelPojo.getInputParams());
        excelPojo.setInputParams(params);
        System.out.println("替换之后的效果：：" + excelPojo.getInputParams());
        //用例执行之前替换{{token}} 为环境变量中保存的对应的值
        String requestHeader = regexReplace(excelPojo.getRequestHeader());
        System.out.println("替换之前的效果：：" + excelPojo.getRequestHeader());
        excelPojo.setRequestHeader(requestHeader);
        System.out.println("替换之后的效果：：" + excelPojo.getRequestHeader());
        String expectedParams = regexReplace(excelPojo.getExpected());
        excelPojo.setExpected(expectedParams);

    }
}
