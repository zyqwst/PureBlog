package com.tale;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

public class Test implements PageProcessor {
	 public static final String URL_LIST = "https://www\\.dy2018\\.com/html/gndy/dyzz/index_\\d+\\.html";
	 

	    public static final String URL_POST = "https://www\\.dy2018\\.com/i/\\d+\\.html";

	    private Site site = Site
	            .me()
	            .setDomain("dy2018.com")
	            .setUserAgent(
	                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	    @Override
	    public void process(Page page) {
	        //列表页
	        if (page.getUrl().regex(URL_LIST).match()) {
	            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST).all());
	            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
	            //文章页
	        } else {
	        	page.putField("title", page.getHtml().css("div.title_all h1").replace("</?h1>", ""));
//	            page.putField("content", page.getHtml().xpath("//div[@id='Zoom']"));
	            page.putField("date",page.getHtml().css("span.updatetime"));
	        }
	    }

	    @Override
	    public Site getSite() {
	        return site; 
	    }

	    public static void main(String[] args) {
	        Spider.create(new Test()).addUrl("https://www.dy2018.com/html/gndy/dyzz/index_2.html")
	                .run();
	    }
}