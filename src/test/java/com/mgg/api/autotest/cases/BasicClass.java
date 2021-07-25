package com.mgg.api.autotest.cases;

import com.alibaba.fastjson.JSONObject;
import com.mgg.api.autotest.util.*;
import com.mgg.api.autotest.pojo.WriteBackData;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.Map;


/**
 * Created by mgg on 2021/7/8
 */

public class BasicClass {
    //log4j添加该类的日志对象
    public Logger logger =  Logger.getLogger(BasicClass.class);

    public String [] callName = {"CaseID","ApiID","Parameter","PreValidateSql",
            "PreValidateResult","AfterValidateSql","AfterValidateResult"};

    @Test(dataProvider = "datesorce")
    public void test(String caseId,
                     String apiId,
                     String parametre,
                     String preValidateSql,
                     String afterValidateSql) {

        //1.调用替换参数的方法，替换掉对应case中的参数化值
        parametre = VariableUtil.replaceVariables(parametre);
        //2.将case传参封装进map集合
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parametre);
        //3.根据接口ID获取对应的接口地址和请求方式
        String url = RaseUtil.getUrlByApiId(apiId);
        String type = RaseUtil.getTypeByApiId(apiId);

        logger.info("调用接口前的数据验证");
        //4.请求前的的数据验证，首先判断是否为空
        if (preValidateSql!=null && preValidateSql.trim().length()>0){
            //4.1.不为空的话替换掉其中的参数变量为实际业务值
            preValidateSql = VariableUtil.replaceVariables(preValidateSql);
            //4.2.执行sql进行查询，获取结果值
            String preValidateResult = DBCheckUtil.doQuery(preValidateSql);
            //4.3.数据回写到指定case中的查询前数据验证
            WriteBackData writeBackData = new WriteBackData("parameter", caseId, "PreValidateResult", preValidateResult);
            //4.4.将所有的WriteBackData对象封装起来，等待执行完一并回写，提高效率
            ExcelUtil.writeBackData.add(writeBackData);
        }
        //5.执行接口请求
        String result = HttpUtil.doService(type, url, params);
        //6.将要回写的数据对象封装到集合中
        WriteBackData writebackdata=new WriteBackData("register",caseId,"ActualResponseData",result);
        ExcelUtil.writeBackData.add(writebackdata);

        //7.执行接口请求后的数据验证，步骤同上面一样
        if (afterValidateSql!=null && afterValidateSql.trim().length()>0){
            afterValidateSql = VariableUtil.replaceVariables(afterValidateSql);
            String afterValidateResult = DBCheckUtil.doQuery(afterValidateSql);
            WriteBackData writeBackData = new WriteBackData("parameter", caseId, "AfterValidateSql", afterValidateResult);
            ExcelUtil.writeBackData.add(writeBackData);
        }
    }

    //8.在测试套件执行完之后，进行批量数据回写
    @AfterSuite
    public void batchWriteBackDatas(){
        ExcelUtil.batchWriteBackeData("D:\\project\\InterfaceAtuoTest\\src\\main\\resources\\DubboTestFile_v2.xlsx");
    }

}
