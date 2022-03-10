package com.mgg.api.autotest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mgg on 2021/7/13
 */

public class PropertiesUtil {

    public static Properties properties = new Properties();

    static {
        try {
            //根据配置文件路径，将文件中的数据读入Properties对象中
            InputStream inStream = new FileInputStream(new File("src/main/resources/config.properties"));
            properties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据K值来获取文件路径值
    public static String getExcelPath(){
        return properties.getProperty("excel.path");
    }

}
