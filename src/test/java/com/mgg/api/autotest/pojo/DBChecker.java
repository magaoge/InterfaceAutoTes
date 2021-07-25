package com.mgg.api.autotest.pojo;

/**
 * Created by mgg on 2021/7/12
 */

public class DBChecker {
    private String no ;
    private String sql ;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "DBChecker{" +
                "no='" + no + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
