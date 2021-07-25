package com.mgg.api.autotest.jdbcutil;



import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mgg on 2021/7/14
 */

public class JDBCUtil {

    public static Properties properties = new Properties();

    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("config.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static Map<String,Object> query(String sql,Object...values){
        Map<String,Object> columnLabelAndValues = null;

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i+1,values[1]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            //获取查询结果信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取查询的结果中有几个字段
            int columnCount = metaData.getColumnCount();

            columnLabelAndValues = new HashMap<String, Object>();

            while (resultSet.next()){
                //i=列数序号
                for (int i = 1; i <= columnCount; i++) {
                    //获取列名
                    String columnLabel = metaData.getColumnLabel(i);
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
