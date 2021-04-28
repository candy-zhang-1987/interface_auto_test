package com.lemon.HomeWork;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HomeWorkDay10 {
    public static void main(String[] args) throws IOException {
        WriteBackData w1=new WriteBackData(1,2,"Pass");
        WriteBackData w2=new WriteBackData(2,2,"Fail");
        WriteBackData w3=new WriteBackData(3,2,"Pass");
        ArrayList<WriteBackData> al=new ArrayList<>();
        al.add(w1);
        al.add(w2);
        al.add(w3);
        FileInputStream fis=new FileInputStream("target/test-classes/exam.xls");
        Workbook sheets = WorkbookFactory.create(fis);
        fis.close();
        Sheet sheet = sheets.getSheetAt(0);
        for(WriteBackData entry:al){
            int cellNum = entry.getCellNum();
            int rowNum = entry.getRowNum();
            String content = entry.getContent();
            Row row =sheet.getRow(rowNum);
            //Cell  cell=row.getCell(cellNum);//使用这个方法遇到空指针异常，因为excel这里是空，需要改用以下方法
            Cell  cell=row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(content);
        }
        FileOutputStream fos=new FileOutputStream("target/test-classes/exam.xls");
        sheets.write(fos);
        fos.close();
    }
}
