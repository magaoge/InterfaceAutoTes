package com.mgg.api.autotest.pojo;

import java.util.Map;

/**
 * Created by mgg on 2021/7/12
 */

public class DBQueryResult {
    private String no ;
    private Map<String ,Object> columenLabelAndValues;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Map<String, Object> getColumenLabelAndValues() {
        return columenLabelAndValues;
    }

    public void setColumenLabelAndValues(Map<String, Object> columenLabelAndValues) {
        this.columenLabelAndValues = columenLabelAndValues;
    }

    @Override
    public String toString() {
        return "DBQueryResult{" +
                "no='" + no + '\'' +
                ", columenLabelAndValues=" + columenLabelAndValues +
                '}';
    }
}
