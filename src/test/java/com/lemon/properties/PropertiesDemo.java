package com.lemon.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties prop=new Properties();
        FileInputStream fis=new FileInputStream("src/test/resources/config.properties");
        System.out.println(prop);
        prop.load(fis);
        fis.close();
        System.out.println(prop);
        prop.getProperty("url");
        //修改配置文件
        prop.setProperty("url","127.0.0.1:3006");
        System.out.println(prop.getProperty("url"));
        FileOutputStream fos=new FileOutputStream("src/test/resources/config2.properties");
        prop.store(fos,"备注");
        fos.close();
    }



}
