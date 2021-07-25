package com.mgg.api.autotest.dubboexcel;

/**
 * Created by mgg on 2021/7/16
 */

public class Test {

    public static void main(String[] args) {
        String[] m = {null,null,null};
        Boolean anImport = isImport(m);
        System.out.println(anImport);
    }

    public static Boolean isImport(String[] m){

        for (int i = 0; i <m.length ; i++) {
            String n = m[i];
            if (n !=null && n.trim().length()>0){
                return false;
            }
        }
        return true;
    }
}
