package com.tale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test implements PageProcessor {
	 public static final String URL_LIST = "https://www.dy2018.com/html/gndy/dyzz/index.*";
	 

	    public static final String URL_POST = "https://www.dy2018.com/i/\\d+\\.html";
	    public static final String URL_POST2 = "https://www.dy2018.com/html/gndy/dyzz/\\d+/\\d+.html";

	    private Site site = Site
	            .me().setRetryTimes(10).setSleepTime(300).setTimeOut(10000)
	            .setDomain("dy2018.com")
	            .setUserAgent(
	                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	    @Override
	    public void process(Page page) {
	        //列表页
	        if (page.getUrl().regex(URL_LIST).match()) {
	            //page.addTargetRequests(page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST).all());
	        	page.putField("list_url", page.getUrl());
	        	page.putField("url", page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST).all());
	        	page.putField("url", page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST2).all());
	            page.addTargetRequests(page.getHtml().xpath("//div[@class='co_content8']").links().regex(URL_LIST).all());
	            //文章页 
	        } 
	        else {
	        	//page.putField("title", page.getHtml().css("div.title_all h1").replace("</?h1>", ""));
	        	page.putField("error_url", page.getUrl());
	        }
	    }

	    @Override
	    public Site getSite() {
	        return site; 
	    }

	    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
	    	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\movie.txt")),"UTF-8"));
	        Spider.create(new Test()).addUrl("https://www.dy2018.com/html/gndy/dyzz/index_120.html")
	        		.thread(4)
	        		.addPipeline(new MyFilePipeline(printWriter))
	                .run();
	        printWriter.close();
	        System.out.println("完成");
	    }
}