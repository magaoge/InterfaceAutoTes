package com.mgg.api.autotest.util;

import com.mgg.api.autotest.pojo.Case;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mgg on 2021/7/6
 */

//真正使用的类
public class CaseUtil {
    //1.声明集合，承接Case对象，类一旦加载，cases中就存好数据对象了
    public static List<Case> cases = new ArrayList<Case>();

    static {
       List<Case> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"parameter",Case.class);
       cases.addAll(list);
    }


    //2.根据静态加载后的所有数据，再根据需要进行筛选自己所需要的接口ID，获取指定对应列的对应数据
    public static Object[][] getCaseDatasByApiId(String apiId,String[] cellNames){
        //6.获取case类反射字节码，不能写入循环，所以在这里声明
        Class<Case> clazz = Case.class;

        //1.声明集合，筛选承接自己所需传参apiId的接口数据Case对象
        ArrayList<Case> csList = new ArrayList<Case>();
        for (Case cs : cases) {
            if (cs.getApiID().equals(apiId)){
                csList.add(cs);
            }
        }
        //2.声明二维数组用来承接筛选好的，行、对应行中列的数据
        Object[][] datas = new Object[csList.size()][cellNames.length];

        for (int i = 0; i < csList.size(); i++) {
            //3.获取csList中的第i个case对象
            Case cs = csList.get(i);
            //cellNames.length是数组长度，实际业务意义是哪几列，有几列
            //4.循环遍历其中所有声明的列值
            for (int j = 0; j <cellNames.length ; j++) {
                //5.拼接获取case对象获取列值得方法名
                String methodName = "get"+cellNames[j];
                try {
                    //7.通过反射调用方法
                    Method method = clazz.getMethod(methodName);
                    //8.使用csList中的第i个case对象来调用对象方法，这里是获取行中每一列的列值
                    Object value = method.invoke(cs);
                    //9.将对应行的每一列的数据存入二维数组
                    datas[i][j] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    //自查验证
    public static void main(String[] args) {

        String [] callName = {"CaseID","ApiID","Parameter"};

        System.out.println(Arrays.toString(callName));

        Object[][] sheet2 = getCaseDatasByApiId("2",callName);
        System.out.println(Arrays.deepToString(sheet2));

        for (Object[] objects : sheet2) {
            for (Object object : objects) {
                System.out.println("【"+object+"】");
            }
        }
    }

}
