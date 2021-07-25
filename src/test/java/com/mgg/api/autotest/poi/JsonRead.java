package com.mgg.api.autotest.poi;


import com.alibaba.fastjson.JSONObject;

import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.Map;

/**
 * Created by mgg on 2021/7/5
 */

public class JsonRead {

    @Test(dataProvider = "datesorce")
    public void test(String json) {

        //将json数据解析封装进Map,其中的数据格式为<<age,10>,<tel,1231241>,......>
        Map<String,String> parse = (Map<String, String>) JSONObject.parse(json);

//		//使用迭代器，获取key
//		Iterator<Map.Entry<String, String>> iterator = parse.entrySet().iterator();
//		while(iterator.hasNext()){
//			Map.Entry<String,String> entry = iterator.next();
//			String key = entry.getKey();
//			String value = String.valueOf(entry.getValue());
//			System.out.println(key+" "+value);
//		}

        Iterator<Map.Entry<String, String>> iterator = parse.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> s = iterator.next();
            String key = s.getKey();
            String value = String.valueOf(s.getValue());
            System.out.println(key+"===="+value);
        }

//        for (String key : parse.keySet()) {
                //由于电话号码被识别为long类型，需要转化String类型
//            String value = String.valueOf(parse.get(key));
//            System.out.println(key+"======="+value);
//        }

    }

    //方法2，可以利用JSONObject.parseObject方法，将json解析，封装为对象
    @Test(dataProvider = "datesorce")
    public  void test2(String json) {
        ParseData parseData = JSONObject.parseObject(json,ParseData.class);

        List<ParseData> l = new ArrayList<ParseData>();
        l.add(parseData);
        for (ParseData data : l) {
            String age = data.getAge();
            String tel = data.getTel();
            System.out.println("age="+age);
            System.out.println("tel="+tel);
        }
    }

    @DataProvider
    public Object[][] datesorce(){
        String filePath = "D:\\project\\InterfaceAtuoTest\\src\\main\\resources\\jsonTestFile.xlsx";
        int[] rows = {2,4,6};
        int[] calls = {2};
        Object[][] sheet1s = dates(filePath, "sheet1", rows, calls);
        return sheet1s;
    }


    //传入参数，可以获取指定行、列的数据
    public static Object[][] dates(
            String filePath,
            String sheetName,
            int[] rows,
            int[] calls)  {

        Object[][] dates = null;

        try {
            //获取Workbook对象，即将整个excol文件读取
            Workbook sheets = WorkbookFactory.create(new File(filePath));
            //获取sheet对象，即要操作整个excol文件中的哪个表
            Sheet sheet = sheets.getSheet(sheetName);
            //设置承接数据的数组容器
            dates = new Object[rows.length][calls.length];
            for (int i = 0; i < rows.length; i++) {
                //获取行
                Row row = sheet.getRow(rows[i]-1);
                for (int j = 0; j < calls.length; j++) {
                    //根据行，通过循环获取对应列，数组中数据为[{列1值，列2值...},{列1值，列2值...}]，每个{}代表每行
                    Cell cell = row.getCell(calls[j]-1);
                    //将列数据类型设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    //获取列值
                    String stringCellValue = cell.getStringCellValue();
                    //将列信息放入对应二级数组，注意这里不能填固定值，否则不就是一直随着循环，将数据放入数组中的固定位置
                    dates[i][j] = stringCellValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }


}
