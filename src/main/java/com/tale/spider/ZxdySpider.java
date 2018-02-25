package com.tale.spider;

import com.tale.spider.formatter.CategoriesFomatter;
import com.tale.spider.formatter.TagsFomatter;
import com.tale.spider.formatter.TimestampFomatter;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Formatter;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

//@HelpUrl(value="https://www.dy2018.com/html/gndy/dyzz/index_\\d+.html",sourceRegion="//div[@class='co_content8']")	
@TargetUrl(value="https://www.dy2018.com/i/\\d+.html",sourceRegion="//div[@class='co_area2']")
public class ZxdySpider {
	@ExtractBy(value = "//div[@class='title_all']/h1/text()",notNull = true)
	private String  title;
	
	@Formatter(value="yyyy-MM-dd",formatter = TimestampFomatter.class)
	@ExtractBy(value = "//span[@class='updatetime']/text()")
    private Integer created;
	
    // 内容文字
	@ExtractBy(value = "//div[@id='Zoom']",notNull = true)
    private String  content;
    

    @ExtractBy(value = "//div[@id='Zoom']/p[6]/text()")
    @Formatter(value="6",formatter = TagsFomatter.class)
    private String  tags;

    @ExtractBy(value = "//div[@id='Zoom']/p[5]/text()")
    @Formatter(formatter = CategoriesFomatter.class)
    private String  categories;

    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "ZxdySpider [title=" + title + ", created=" + created + ", tags=" + tags + ", categories=" + getCategories()
				+ "]";
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}
}
