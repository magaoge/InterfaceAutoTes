package com.mgg.api.autotest.poi;

import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by mgg on 2021/7/5
 */

public class ExcolRead {


    @Test(dataProvider = "datesorce")
    public void test(String age, String tel) {
        System.out.println(age+"========="+tel);
    }

    @DataProvider
    public Object[][] datesorce(){
        String filePath = "D:\\project\\InterfaceAtuoTest\\src\\main\\resources\\testFile.xlsx";
        Object[][] sheet1s = dates(filePath, "sheet1", 2, 8, 2, 3);
        return sheet1s;
    }

    //传入参数，可以获取连续行数的数据
    public static Object[][] dates(
            String filePath,
            String sheetName,
            int starRow,
            int endRow,
            int starCall,
            int endCall)  {

        Object[][] dates = null;

        try {
            //获取Workbook对象，即将整个excol文件读取
            Workbook sheets = WorkbookFactory.create(new File(filePath));
            //获取sheet对象，即要操作整个excol文件中的哪个表
            Sheet sheet = sheets.getSheet(sheetName);
            //设置承接数据的数组容器
            dates = new Object[endRow-starRow+1][endCall-starCall+1];
            for (int i = starRow; i <= endRow; i++) {
                //获取行
                Row row = sheet.getRow(i-1);
                for (int j = starCall; j <= endCall; j++) {
                    //根据行，通过循环获取对应列，数组中数据为[{列1值，列2值...},{列1值，列2值...}]，每个{}代表每行
                    Cell cell = row.getCell(j-1);
                    //Row.MissingCellPolicy.CREATE_NULL_AS_BLANK：避免用例设计空值，报错
//                    Cell cell = row.getCell(j-1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //将列数据类型设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    //获取列值
                    String stringCellValue = cell.getStringCellValue();
                    //将列信息放入对应二级数组，注意这里不能填固定值，否则不就是一直随着循环，将数据放入数组中的固定位置
                    dates[i-starRow][j-starCall] = stringCellValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }


}
