package com.young.utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by honey on 2017/7/20.
 */
public class ScrDaiLi implements PageProcessor{

    public static String dailiUrl = "http://www.xicidaili.com/nn/";
    private Map<String,List<String>> proxys = new HashMap<String, List<String>>();

    public void process(Page page) {
        List<String> ips = page.getHtml().xpath("//*[@id=\"ip_list\"]/tbody/tr/td[2]/text()").all();
        List<String> ports = page.getHtml().xpath("//*[@id=\"ip_list\"]/tbody/tr/td[3]/text()").all();
        proxys.put("ips",ips);
        proxys.put("ports",ports);
    }

    public Site getSite() {
        return Site.me();
    }

    public Map<String, List<String>> getProxys() {
        return proxys;
    }

    public void proxys(){
        Spider.create(this)
//                .addPipeline(new ConsolePipeline())
                .addRequest(new Request(ScrDaiLi.dailiUrl))
                .thread(1)
                .run();
    }

    public static void main(String[] args) {
        ScrDaiLi scrDaiLi = new ScrDaiLi();
        Spider.create(scrDaiLi)
                .addPipeline(new ConsolePipeline())
                .addRequest(new Request(ScrDaiLi.dailiUrl))
                .thread(1)
                .run();
    }
}
