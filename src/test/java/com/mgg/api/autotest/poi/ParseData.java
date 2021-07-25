package com.mgg.api.autotest.poi;

/**
 * Created by mgg on 2021/7/5
 */

public class ParseData {
    private String age;
    private String tel;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "ParseData{" +
                "age='" + age + '\'' +
                ", tel=" + tel +
                '}';
    }
}
