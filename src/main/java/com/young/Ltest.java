package com.young;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by honey on 2017/7/20.
 */
public class Ltest {

    public static float disInterval = 0.0003f;
    public static float initLat = 34.216498f;
    public static float initLon = 108.892287f;

    public static void main(String[] args) throws UnsupportedEncodingException {
        
        PageProcessor pageProcessor = new sd_notice();
        Spider spider = Spider.create(pageProcessor);
        for (int i = 0; i < 50; i++) {
            float lat = initLat - disInterval*(float)i;
            String url = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cityCode","029");
            params.put("latitude",String.valueOf(lat));
            params.put("scope","10");
            params.put("client_id","android");
            params.put("longitude",String.valueOf(initLon));

            url = "https://api.mobike.com/mobike-api/rent/v2/nearbyBikesInfo.do";
            Request request = new Request(url);
            request.setRequestBody(HttpRequestBody.form(params,"utf-8"));
            request.setMethod(HttpConstant.Method.POST);
            spider.addRequest(request);
        }
        long a = System.currentTimeMillis();
        spider.thread(5).run();
        long b = System.currentTimeMillis();
        System.out.println(b-a);
    }
}
