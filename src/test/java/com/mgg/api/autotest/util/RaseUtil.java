package com.mgg.api.autotest.util;

import com.mgg.api.autotest.pojo.Rase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgg on 2021/7/6
 */

//真正使用的类
public class RaseUtil {
    //声明集合，承接Case对象，类一旦加载，cases中就存好数据对象了
    public static List<Rase> rases = new ArrayList<Rase>();

    static {
        List<Rase> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"api",Rase.class);
        rases.addAll(list);
    }

    //根据接口id,获取接口地址
    public static String getUrlByApiId(String apiId){
        for (Rase rase : rases) {
            if(rase.getApiID().equals(apiId)){
                return rase.getUrl();
            }
        }
        return "";
    }

    //根据接口id,获取接口请求类型
    public static String getTypeByApiId(String apiId){
        for (Rase rase : rases) {
            if(rase.getApiID().equals(apiId)){
                return rase.getType();
            }
        }
        return "";
    }

}
