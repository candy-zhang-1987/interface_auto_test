package com.lemon.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDemo {
    public static void main(String[] args) throws IOException {
        //解析excel:读和写
/*
1 加载excel文件
2 POI解析excel(EXCEL---sheet---行---单元格cell)
3 获取excel对象
4 获取sheet
5 获取row
6 获取cell
7 打印内容
 */
        FileInputStream fis=new FileInputStream("src/test/resources/excelDemo.xlsx");
        //获取excel对象
        Workbook sheets = WorkbookFactory.create(fis);
        fis.close();
        //获取sheet对象
        Sheet sheet = sheets.getSheetAt(0);
        //获取row对象
        Row row = sheet.getRow(2);
        //获取cell
        Cell cell = row.getCell(1);
        //获取到了单个cell的值
        System.out.println(cell.getStringCellValue());
        //获取整个excel的内容，方法一ForEach
        for(Row row1:sheet){
            for(Cell cell1:row1){
                cell1.setCellType(CellType.STRING);
                System.out.print(cell1.getStringCellValue()+",");
            }
            System.out.println();
        }
        //获取整个excel的内容，方法二，上面的方法的缺点是只能遍历整个表，如果我不想要表头，可以采用以下方法
        for(int i =1;i<=sheet.getLastRowNum();i++){
            Row row2 = sheet.getRow(i);
            for(int j=0;j<row2.getLastCellNum();j++){
                Cell cell2 = row2.getCell(j);
                cell2.setCellType(CellType.STRING);
                System.out.print(cell2.getStringCellValue()+",");
            }
            System.out.println();
        }
        cell.setCellType(CellType.STRING);
        cell.setCellValue("wangwuwu");
        FileOutputStream fos=new FileOutputStream("src/test/resources/excelDemo1.xlsx");
        //把JAVA内存中的数据写入文件中
        sheets.write(fos);
        fos.close();
    }
}
