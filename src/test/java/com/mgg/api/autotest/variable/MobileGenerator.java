package com.mgg.api.autotest.variable;

import com.mgg.api.autotest.util.JDBCUtil;

import java.util.Map;

/**
 * Created by mgg on 2021/7/13
 */
//生成动态参数的反射类，供变量参数化类使用
public class MobileGenerator {
    public String generateToBeRegisterMobile(){
        //1.获取数据库中电话号码最大的数字加1，并且拼接字符串“”，使其原格式展示
        String sql = "select concat(max(mobilephone)+1,'') as toBeRegisterMobile from memeber";
        //2.查询结果，并且封装map
        Map<String,Object> columnLableAndValues = JDBCUtil.query(sql);
        return columnLableAndValues.get("toBeRegisterMobile").toString();
    }

    public String generateSystemNotExistMobile(){
        //concat作用是将两个字符串拼接起来，用来避免电话号展示为long类型
        String sql = "select concat(max(mobilephone)+2,'') as toBeRegisterMobile from memeber";
        Map<String,Object> columnLableAndValues = JDBCUtil.query(sql);
        return columnLableAndValues.get("toBeRegisterMobile").toString();
    }
}
