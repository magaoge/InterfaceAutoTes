package com.mgg.api.autotest.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mgg on 2021/7/14
 */

public class JDBCUtil {

    //1.准备Properties对象，承接数据库连接信息
    public static Properties properties = new Properties();
    //2.准备配置文件，里面配置数据库连接信息，读取进Properties对象
    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("config.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.获取数据库连接对象
    public static Connection getConnection()  {
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //4.根据传入sql查询
    public static Map<String,Object> query(String sql,Object...values){
        Map<String,Object> columnLabelAndValues = null;

        try {
            //5.获取数据库sql预编译对象
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

            //6.将传入的可变参数按照顺序替换进sql中的占位符
            // JDBC中PreparedStatement.setObject（index，Object）方法index从1开始
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i+1,values[i]);
            }
            //7.执行sql获取结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            //8.获取查询结果信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            //9.获取查询的结果中有几个字段
            int columnCount = metaData.getColumnCount();
            //10.预备存放sql查询结果的map集合
            columnLabelAndValues = new HashMap<String, Object>();

            while (resultSet.next()){
                //11.获取列名与列值的关系，存入集合
                //i=列数序号
                for (int i = 1; i <= columnCount; i++) {
                    //12.获取列名
                    String columnLabel = metaData.getColumnLabel(i);
                    //13.获取列值
                    String columnValue = resultSet.getObject(columnLabel).toString();
                    columnLabelAndValues.put(columnLabel,columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnLabelAndValues;
    }
}
