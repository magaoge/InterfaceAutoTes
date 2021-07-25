package com.mgg.api.autotest.util;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * Created by mgg on 2021/7/8
 */

public class HttpUtil {

    public static Map<String,String> cookies = new HashMap<String, String>();

    //一、post请求
    public static String doPast(String url, Map<String, String> prama) {

        //1.声明post请求
        HttpPost httpPost = new HttpPost(url);
        //2.BasicNameValuePair类可自动将K,V封装为K=V的的形式
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //3.获取请求参数中所有K的数组
        Set<String> paramKeys = prama.keySet();
        //4.遍历数组
        for (String paramKey : paramKeys) {
            //5.获取对应值
            String paramValue =  prama.get(paramKey);
            //5.将对应的参数添加进请求参数中
            parameters.add(new BasicNameValuePair(paramKey, paramValue));
        }

        String result = "";
        try {
            //6.将请求参数全部放入httpPost请求中，并且声明字符集
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
            //7.创建、声名客户端
            HttpClient client = HttpClients.createDefault();
            //8.添加cookie请求头信息（如果是第一个接口，肯定没有cookie，所以方法中设定有非空校验）
            addCookisInRequestHeaderBeforeRequest(httpPost);
            //9.发起请求
            HttpResponse response = client.execute(httpPost);
            //10.获取cookie
            getAndPostCookiesFromResponseHeader(httpPost);
            //11.获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            //8.获取响应报文
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String doGet(String url, Map<String, String> prama) {

        Set<String> paramKeys = prama.keySet();

        int mark = 1;
        for (String paramKey : paramKeys) {
            String paramValue = prama.get(paramKey);
            if (mark == 1) {
                url += "?" + paramKey + "=" + paramValue;
            } else {
                url += "&" + paramKey + "=" + paramValue;
            }
            mark++;
        }

        HttpGet httpGet = new HttpGet(url);
        String result = "";
        try {
            HttpClient client = HttpClients.createDefault();
            addCookisInRequestHeaderBeforeRequest(httpGet);
            HttpResponse response = client.execute(httpGet);
            getAndPostCookiesFromResponseHeader(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //根据获取到的请求方式来调用请求方法
    public static String doService(String type, String url, Map<String, String> params) {
        String result = "";
        if (type.equals("post")) {
            result = doPast(url, params);
        } else if (type.equals("get")) {
            result = doGet(url, params);
        }
        return result;
    }

    //传入请求
    private static void getAndPostCookiesFromResponseHeader(HttpRequest request) {
        //1.根据"Set-cookie"关键字来获取相应头中的cookie信息
        Header setCookisHeader = request.getFirstHeader("Set-cookie");
        //2.判断"Set-cookie"值（K,V信息不为空）不为空
        if (setCookisHeader!=null){
            //3.获取cookies值
            String cookiePairsString = setCookisHeader.getValue();
            //4.判断cookie值不为空
            if (cookiePairsString != null && cookiePairsString.trim().length()>0){
                //5.根据";"切分"Set-cookie"中的所有cookies信息
                String[] cookiesPair = cookiePairsString.split(";");
                //6.判断cookiesPair数组不为空
                if (cookiesPair!=null) {
                    //7.遍历cookiesPair数组，如果有符合"JSESSIONGID"关键字的cookie信息，则取出来放入cookies集合中去
                    for (String cookie : cookiesPair) {
                        if (cookie.contains("JSESSIONGID")){
                            cookies.put("JSESSIONGID",cookie);
                        }
                    }
                }
            }
        }
    }

    //请求头添加cookie信息，传入参数为“请求方式的请求变量”
    private static void addCookisInRequestHeaderBeforeRequest(HttpRequest request) {
        String jessionCookie = cookies.get("JSESSIONGID");
        if (jessionCookie!=null){
            request.setHeader("Cookie",jessionCookie);
        }
    }

}
