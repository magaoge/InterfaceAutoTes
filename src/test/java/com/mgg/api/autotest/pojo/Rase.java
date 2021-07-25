package com.mgg.api.autotest.pojo;

/**
 * Created by mgg on 2021/7/6
 */

public class Rase {
    private String ApiID;
    private String ApiName;
    private String Type;
    private String Url;

    public String getApiID() {
        return ApiID;
    }

    public void setApiID(String apiID) {
        ApiID = apiID;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "Rase{" +
                "ApiID='" + ApiID + '\'' +
                ", ApiName='" + ApiName + '\'' +
                ", Type='" + Type + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
