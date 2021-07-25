package com.mgg.api.autotest.util;

import com.mgg.api.autotest.pojo.Variable;
import com.mgg.api.autotest.pojo.WriteBackData;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by mgg on 2021/7/13
 */
//参数化类
public class VariableUtil {
    //1.承接表中行列数据的Map
    public static Map<String,String> variableNameAndvariableValue = new HashMap<String, String>();
    //1.声明对象集合
    public static List<Variable> variables = new ArrayList<Variable>();

    static {
        //2.将表中所有数据封装成对象，然后装入集合
        List<Variable> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"variable",Variable.class);
        variables.addAll(list);
        //3.获取指定行列对应关系，并且如果获取到空值，则调用反射类，生成数据，并且进行数据回写，记录参数
        loadVariableToMap();
        //4.获取该表单的行和对应行得索引，列名和对应列索引，因为测试套件在批量回写的时候，需要用到
        //cellNameCellNumMapping和rowidentifyRowNumMapping这两个集合，所以需要调用这个方法要先加载到内存中
        ExcelUtil.loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"variable");
    }

    //获取指定行列对应关系，并且如果获取到空值，则调用反射类，生成数据，并且进行数据回写，记录参数
    private static void loadVariableToMap() {
        //1.遍历集合中对象
        for (Variable variable : variables) {
            //2.将对象所对应的行列的（每个对象是每行，对象中的数据是列数据）列名与值取出（getValueName是列名）
            String valueName = variable.getValueName();
            String value = variable.getValue();
            //3.如果取出的值为空，则取出需要使用的反射类，反射方法，因为这里需要进行动态参数化
            if(value == null && value.trim().length()==0){
                String reflectClass = variable.getReflectClass();
                String reflectMethod = variable.getReflectMethod();
                try {
                    //3.1获取反射类的字节码
                    Class clazz = Class.forName(reflectClass);
                    //3.2获取反射类对象
                    Object obj = clazz.newInstance();
                    //3.3通过反射类对象，声明要调用的反射方法
                    Method method = clazz.getMethod(reflectMethod);
                    //3.4调用方法，获取值
                    value =(String) method.invoke(obj);
                    //4.将获取的值进行数据回写，以便后面去查看动态生成的测试数据
                    ExcelUtil.writeBackData.add(new WriteBackData("variable",valueName,"ReflectValue",value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //5.将变量名与变量值封装进Map集合
            variableNameAndvariableValue.put(valueName,value);
        }
    }

    //用来替换表中的参数为有意义的参数值
    public static String replaceVariables(String parametre) {
        //1.获取该表中所有列名的集合
        Set<String> variableNames = variableNameAndvariableValue.keySet();
        //2.遍历列名
        for (String variableName : variableNames) {
            //3.如果传入的parametre中，包含variableName，则将parametre中的variableName替换为variableName对应的值
            if (parametre.contains(variableName)){
                parametre = parametre.replace(variableName,variableNameAndvariableValue.get(variableName));
            }
        }
        return parametre;
    }
}
