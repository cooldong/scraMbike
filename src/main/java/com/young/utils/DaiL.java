package com.young.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by honey on 2017/7/20.
 */
public class DaiL {
    public static void main(String[] args) {
        getDailis();
    }

     public static String[] getDailis(){
        String[] reslist = new String[0];
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://jsonblob.com/api/jsonBlob/31bf2dc8-00e6-11e7-a0ba-e39b7fdbe78b");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity());
            reslist = res.substring(1,res.length()-1).split(",",500);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reslist;

    }

}
