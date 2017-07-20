package com.young;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by honey on 2017/7/20.
 */
public class sd_notice implements PageProcessor {
    public void process(Page page) {
        System.out.println("dow");
    }

    public Site getSite() {
        return Site.me()
                    .setSleepTime(3000)
                    .setUseGzip(true)
                    .setRetryTimes(5)
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .addHeader("charset","UTF-8")
                    .addHeader("Host","api.mobike.com")
                    .addHeader("Connection","Keep-Alive")
                    .addHeader("Accept-Encoding","gzip")
                    .addHeader("platform","1")
                    .addHeader("mobileNo","15991685275")
                    .addHeader("lang","zh")
                    .addHeader("version","5.2.1")
                    .addHeader("citycode","029")
                    .addHeader("accesstoken","dcf0d5aea7c163a1884942f53014cc3b")
                    .addHeader("os","25");
    }
}
