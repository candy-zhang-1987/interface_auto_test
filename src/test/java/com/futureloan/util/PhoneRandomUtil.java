package com.futureloan.util;

import org.testng.annotations.Test;

import java.util.Random;

public class PhoneRandomUtil {
    public static String phoneRandomgenerate(){
        Random random = new Random();
        String phonePrefix="139";
        for(int i=1;i<9;i++){
         Object num= random.nextInt(9);
            phonePrefix=phonePrefix+num;
        }
        System.out.println(phonePrefix);
        String sql="select count(*) from member where mobile_phone=" + phonePrefix;
        long result= JDBCUtils.queryCountData(sql);
        if(result==0){
        }else{
            phoneRandomgenerate();
        }
        return phonePrefix;
            }
}
