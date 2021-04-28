package com.lemon.HomeWork;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeWorkDay9Section1 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("src/test/resources/exam.xls");
        Workbook sheets = WorkbookFactory.create(fis);
        fis.close();
        //读取全部数据，方式一
        Sheet sheet = sheets.getSheetAt(0);
        for(int i=0;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++){
                Cell cell = row.getCell(j);
                cell.setCellType(CellType.STRING);
                System.out.print(cell.getStringCellValue()+",");
            }
            System.out.println();
        }
        //读取全部数据，方式二
        for(Row row:sheet){
            for(Cell cell:row){
                cell.setCellType(CellType.STRING);
                System.out.print(cell.getStringCellValue()+",");
            }
            System.out.println();
        }
    }
}
