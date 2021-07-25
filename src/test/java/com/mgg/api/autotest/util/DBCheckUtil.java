package com.mgg.api.autotest.util;

import com.alibaba.fastjson.JSONObject;
import com.mgg.api.autotest.pojo.DBChecker;
import com.mgg.api.autotest.pojo.DBQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mgg on 2021/7/12
 */

public class DBCheckUtil {
    public static String doQuery(String valiDatesql) {
        //1.将传入的json数据解析，再以DBChecker对象形式封装进数组
        List<DBChecker> dbCheckers = JSONObject.parseArray(valiDatesql, DBChecker.class);
        //2.准备承接数据查询结果对象的数组
        ArrayList<DBQueryResult> dbQueryResults = new ArrayList<DBQueryResult>();

        //3.遍历数组，取得其中的DBChecker对象
        for (DBChecker dbChecker : dbCheckers) {
            String no = dbChecker.getNo();
            String sql = dbChecker.getSql();
            //4.查询其中的sql，然后将结果集封装为map集合
            Map<String, Object> columenLabelAndValues = JDBCUtil.query(sql);
            //5.创建DBQueryResult对象，以便每次都来承接执行的结果，生成对应关系
            DBQueryResult dbQueryResult = new DBQueryResult();
            //6.将对应执行DBChecker对象的sql编号放入DBQueryResult对象中
            dbQueryResult.setNo(no);
            //7.将对应执行DBChecker对象的sql结果map放入DBQueryResult对象中
            dbQueryResult.setColumenLabelAndValues(columenLabelAndValues);
            //8.将结果对象放入提前准备好的数组中
            dbQueryResults.add(dbQueryResult);
        }
        //9.转换数组格式为json字符串，返回
        return JSONObject.toJSONString(dbQueryResults);
    }
}
