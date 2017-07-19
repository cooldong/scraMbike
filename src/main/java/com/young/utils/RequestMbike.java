package com.young.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by honey on 2017/7/19.
 */
public class RequestMbike {
    public static void main(String[] args) {
        for (int i=0;i<2;i++){
            httpPost((float)i);
        }
        System.out.println("cf:"+cf);
    }

    public static float disInterval = 0.0003f;
    public static float initLat = 34.216498f;
    public static float initLon = 108.892287f;
    public static List<String> ids = new ArrayList<String>();
    public static int cf = 0;
    public static float bottom = 34.3570f;
    public static float top = 34.1873f;
    public static float left = 108.8504f;
    public static float right = 109.0599f;


    public static void httpPost(float index){
//        34.1
//        34.3533569871,108.8460159302
//        34.1873818600,108.8504791260
//        34.1896538139,109.0599060059
//        34.3570416008,109.0454864502
        float lat = initLat - disInterval*index;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://api.mobike.com/mobike-api/rent/v2/nearbyBikesInfo.do");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cityCode","029"));
        params.add(new BasicNameValuePair("latitude",String.valueOf(lat)));
        params.add(new BasicNameValuePair("scope","10"));
        params.add(new BasicNameValuePair("client_id","android"));
        params.add(new BasicNameValuePair("longitude",String.valueOf(initLon)));

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type","application/x-www-form-urlencoded"));
        headers.add(new BasicHeader("charset","UTF-8"));
        headers.add(new BasicHeader("Host","api.mobike.com"));
        headers.add(new BasicHeader("Connection","Keep-Alive"));
        headers.add(new BasicHeader("Accept-Encoding","gzip"));
        headers.add(new BasicHeader("platform","1"));
        headers.add(new BasicHeader("mobileNo","15991685275"));
        headers.add(new BasicHeader("lang","zh"));
        headers.add(new BasicHeader("version","5.2.1"));
        headers.add(new BasicHeader("citycode","029"));
        headers.add(new BasicHeader("accesstoken","dcf0d5aea7c163a1884942f53014cc3b"));
        headers.add(new BasicHeader("os","25"));

        UrlEncodedFormEntity uefEntity;
        try{
            uefEntity = new UrlEncodedFormEntity(params,"UTF-8");
            httppost.setHeaders((Header[]) headers.toArray(new Header[0]));
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try{
                HttpEntity entity = response.getEntity();
                String resJson = EntityUtils.toString(entity);
                JSONObject jsonObject = JSON.parseObject(resJson);
                JSONArray bikes = jsonObject.getJSONArray("bike");
                System.out.println(bikes.size());
                for (Object object:bikes){
//                    insert((JSONObject) object);
                    JSONObject object1 = (JSONObject) object;
                    if(ids.contains(object1.getString("distId"))){
                        cf++;
                    }else {
                        ids.add(object1.getString("distId"));
                    }
                }
            }finally{
                response.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                httpclient.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void insert(JSONObject object){
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/db-mbikes?"
                + "user=root&password=111111&useUnicode=true&characterEncoding=UTF8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url);
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO mbikes(bikeid,bikex,bikey) VALUES (" + object.getString("distId") + "," + object.getString("distX") + "," +  object.getString("distY")+ ")";
            int result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
