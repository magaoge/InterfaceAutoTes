package com.mgg.api.autotest.pojo;

/**
 * Created by mgg on 2021/7/6
 */

public class Case {
    private String CaseID;
    private String Desc;
    private String ApiID;
    private String Parameter;
    private String ExpectedResponseData;
    private String ActualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;

    public String getPreValidateSql() {
        return PreValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        PreValidateSql = preValidateSql;
    }

    public String getPreValidateResult() {
        return PreValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        PreValidateResult = preValidateResult;
    }

    public String getAftVrvalidateSql() {
        return AfterValidateSql;
    }

    public void setAftVrvalidateSql(String aftVrvalidateSql) {
        AfterValidateSql = aftVrvalidateSql;
    }

    public String getAfterValidateResult() {
        return AfterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        AfterValidateResult = afterValidateResult;
    }

    public String getExpectedResponseData() {
        return ExpectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        ExpectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return ActualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        ActualResponseData = actualResponseData;
    }

    public String getCaseID() {
        return CaseID;
    }

    public void setCaseID(String caseID) {
        CaseID = caseID;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getApiID() {
        return ApiID;
    }

    public void setApiID(String apiID) {
        ApiID = apiID;
    }

    public String getParameter() {
        return Parameter;
    }

    public void setParameter(String parameter) {
        Parameter = parameter;
    }

    @Override
    public String toString() {
        return "Case{" +
                "CaseID='" + CaseID + '\'' +
                ", Desc='" + Desc + '\'' +
                ", ApiID='" + ApiID + '\'' +
                ", Parameter='" + Parameter + '\'' +
                ", ExpectedResponseData='" + ExpectedResponseData + '\'' +
                ", ActualResponseData='" + ActualResponseData + '\'' +
                ", PrevalidateSql='" + PreValidateSql + '\'' +
                ", PrevalidateResult='" + PreValidateResult + '\'' +
                ", AftervalidateSql='" + AfterValidateSql + '\'' +
                ", AftervalidateResult='" + AfterValidateResult + '\'' +
                '}';
    }
}
