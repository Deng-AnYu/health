package com.my.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: Deng
 * @date: 2020-04-06 19:39
 * @description:
 */
public class TestExcel {

//    @Test
    public void test() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook("C:\\Users\\Deng\\Desktop\\测试.xlsx");
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        XSSFRow row1 = sheetAt.getRow(0);
        XSSFRow row2 = sheetAt.getRow(1);
        for (Cell cell : row1) {
            String get = cell.getStringCellValue();
            System.out.println(get);
        }
    }

//    @Test
    public void test2() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook("C:\\Users\\Deng\\Desktop\\测试.xlsx");
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        int firstRowNum = sheetAt.getFirstRowNum();
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            for (Cell cell : row) {
                String get = cell.getStringCellValue();
                System.out.println(get);
            }
        }
    }

//    @Test
    public void test3() throws IOException {
//        创建excel
        XSSFWorkbook sheets = new XSSFWorkbook();
        XSSFSheet sheet = sheets.createSheet();
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell = row1.createCell(0);
        row1.createCell(0).setCellValue("标题1");
        row1.createCell(1).setCellValue("标题2");
        XSSFRow row2 = sheet.createRow(1);
        XSSFCell cell1 = row2.createCell(0);
        row2.createCell(0).setCellValue("内容1");
        row2.createCell(1).setCellValue("内容2");

        FileOutputStream out = new FileOutputStream("F:\\test.xlsx");
        sheets.write(out);
        out.flush();//刷新
        out.close();//关闭
        sheets.close();
    }


}

