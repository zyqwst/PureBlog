package com.tale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tale.model.entity.Contents;
import com.tale.spider.formatter.CategoriesFomatter;
import com.tale.spider.formatter.TimestampFomatter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test implements PageProcessor {
	 public static final String URL_LIST = "https://www.dy2018.com/html/gndy/dyzz/index.*";
	 private Logger logger = LoggerFactory.getLogger(getClass());

	    public static final String URL_POST = "https://www.dy2018.com/i/\\d+\\.html";
	    public static final String URL_POST2 = "https://www.dy2018.com/html/gndy/dyzz/\\d+/\\d+.html";

	    private Site site = Site
	            .me().setRetryTimes(10).setSleepTime(300).setTimeOut(10000)
	            .setDomain("dy2018.com")
	            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	    @Override
	    public void process(Page page) {
	        //列表页
	        if (page.getUrl().regex(URL_LIST).match()) {
	            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST).all());
	            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"co_content8\"]").links().regex(URL_POST2).all());
	            page.addTargetRequests(page.getHtml().xpath("//div[@class='co_content8']").links().regex(URL_LIST).all());
	            //文章页 
	        } 
	        else {
	        	Contents contents = new Contents();
				contents.setAuthorId(2);
				contents.setFmtType("html");
				contents.setType("post");
				contents.setStatus("publish");
				contents.setTitle(page.getHtml().xpath("//div[@class='title_all']/h1/text()").toString());
				try {
					TimestampFomatter tf = new TimestampFomatter("yyyy-MM-dd");
					contents.setCreated(tf.format(page.getHtml().xpath("//span[@class='updatetime']/text()").get()));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				contents.setContent(page.getHtml().xpath("//div[@id='Zoom']")
						.replace("请把www.dy2018.com分享给你的朋友,更多人使用，速度更快 电影天堂www.dy2018.com欢迎你每天来!",
								"请把 超级电影 www.sviip.com分享给你的朋友,更多人使用，速度更快 超级电影www.sviip.com欢迎你每天来!")
						.get());
	
				contents.setTags(page.getHtml().xpath("//div[@class='co_content8']//div[@class='position']/span[2]/html()")
						.replace("类型：|<a href=\\S+\">|</a>", "").replace("\\s*", "").replace("/", ",").get());
	
				try {
					contents.setCategories(new CategoriesFomatter()
							.format(page.getHtml().xpath("//div[@id='Zoom']/html()").get().substring(0, 200)));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				page.putField("contents", contents);
	        }
	    }

	    @Override
	    public Site getSite() {
	        return site; 
	    }

	    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
	    	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\movie.txt")),"UTF-8"));
	        Spider.create(new Test()).addUrl("https://www.dy2018.com/html/gndy/dyzz/index_270.html")
	        		.thread(4)
	        		.addPipeline(new MyFilePipeline(printWriter))
	                .run();
	        printWriter.close();
	        System.out.println("完成");
	    }
}