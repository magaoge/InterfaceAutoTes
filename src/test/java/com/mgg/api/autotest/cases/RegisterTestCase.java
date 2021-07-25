package com.mgg.api.autotest.cases;

import com.mgg.api.autotest.util.CaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

/**
 * Created by mgg on 2021/7/5
 */

//register接口
public class RegisterTestCase extends BasicClass {

    @DataProvider
    public Object[][] datesorce(){

        Object[][] registerData = CaseUtil.getCaseDatasByApiId("1",callName);
        return registerData;
    }


    public  void get() {
        //1.声名url地址
        String url = "locahouse/login";
        //2.将测试数据拼接进url
        String username = "magaoge";
        String password = "123456";
        url+="?username="+username+"&password="+password;
        System.out.println(url);
        //4.指定接口请求方式
        HttpGet httpGet = new HttpGet(url);
        //3.获取客户端
        CloseableHttpClient client = HttpClients.createDefault();
        //4.请求客户端
        HttpResponse response = null;
        try {
            response = client.execute(httpGet);
            //5.获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            //6.获取响应报文
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
