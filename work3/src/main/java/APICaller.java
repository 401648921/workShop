import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class APICaller {
    public APICaller() {
        this.clientConnectionManager = new PoolingHttpClientConnectionManager();
        this.clientConnectionManager.setMaxTotal(60);
        this.clientConnectionManager.setDefaultMaxPerRoute(10);
    }

    public PoolingHttpClientConnectionManager clientConnectionManager;   //连接池

    public  String result(String country){
        //基地址
        String requestUrl = "https://covid-api.mmediagroup.fr/v1";
        //请求地址
        String url = requestUrl+"/cases?country="+country;
        //调用api
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        try{
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                if(response.getEntity()!=null){
                    return EntityUtils.toString(response.getEntity(),"utf-8");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null)
            {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //失败就放回空串
        return "";

    }
    public RequestConfig getConfig(){
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000000)   //创建连接最长时间
                .setConnectionRequestTimeout(500000)    //获取连接最长时间
                .setSocketTimeout(1000000)            //数据传输最长时长
                .build();
        return config;
    }

    public static void main(String[] args) {
        APICaller apiCaller = new APICaller();
        CountryCase countryCase = new CountryCase(JsonUnhand.JsonToMap(apiCaller.result("France")).get("All"));
        //System.out.println(apiCaller.result("China"));
    }
}
