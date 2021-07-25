package com.mgg.api.autotest.cases;

import com.mgg.api.autotest.util.CaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mgg on 2021/7/5
 */

//login接口
public class LoginTestCase extends BasicClass {

    @DataProvider
    public Object[][] datesorce(){

        Object[][] loginData = CaseUtil.getCaseDatasByApiId("2",callName);
        return loginData;
    }

    public  void post(String urla, Map<String ,String> param) {
        //1.声名地址
        String url = "http://localhost:8888/postNoParamDemo";
        //2.指定接口请求方式为post
        HttpPost httpPost = new HttpPost(url);
        //5.准备测试数据
        String username = "magaoge";
        String password = "123456";
        //6.将测试数据封装,跟随url一同传递
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("username",username));
        parameters.add(new BasicNameValuePair("password",password));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
            //3.创建、声名客户端
            HttpClient client = HttpClients.createDefault();
            //4.发起请求
            HttpResponse response = client.execute(httpPost);
            //7.获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            //8.获取响应报文
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
