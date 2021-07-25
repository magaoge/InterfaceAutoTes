package com.mgg.api.autotest.pojo;

/**
 * Created by mgg on 2021/7/13
 */

public class Variable {
    private String ValueName;
    private String Value;
    private String ReflectClass;
    private String ReflectMethod;
    private String ReflectValue;
    private String Remarks;

    public String getReflectClass() {
        return ReflectClass;
    }

    public void setReflectClass(String reflectClass) {
        ReflectClass = reflectClass;
    }

    public String getReflectMethod() {
        return ReflectMethod;
    }

    public void setReflectMethod(String reflectMethod) {
        ReflectMethod = reflectMethod;
    }

    public String getReflectValue() {
        return ReflectValue;
    }

    public void setReflectValue(String reflectValue) {
        ReflectValue = reflectValue;
    }



    public String getValueName() {
        return ValueName;
    }

    public void setValueName(String valueName) {
        ValueName = valueName;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "ValueName='" + ValueName + '\'' +
                ", Value='" + Value + '\'' +
                ", ReflectClass='" + ReflectClass + '\'' +
                ", ReflectMethod='" + ReflectMethod + '\'' +
                ", ReflectValue='" + ReflectValue + '\'' +
                ", Remarks='" + Remarks + '\'' +
                '}';
    }
}
