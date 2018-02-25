package com.tale.spider;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.tale.model.entity.Contents;
import com.tale.service.ContentsService;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

@Bean
public class DataPipeline implements PageModelPipeline<ZxdySpider> {
	
	@Inject
    private ContentsService contentsService;

	@Override
	public void process(ZxdySpider t, Task task) {
	  Contents contents = new Contents();
	  contents.setAuthorId(2);
	  contents.setCategories(t.getCategories());
	  contents.setContent(t.getContent().replace("请把www.dy2018.com分享给你的朋友,更多人使用，速度更快 电影天堂www.dy2018.com欢迎你每天来!", "请把www.sviip.com分享给你的朋友,更多人使用，速度更快 超级电影 www.sviip.com欢迎你每天来!"));
	  contents.setCreated(t.getCreated());
	  contents.setFmtType("html");
	  contents.setTags(t.getTags());
	  contents.setTitle(t.getTitle());
	  contents.setType("post");
	  contents.setStatus("publish");
	  Long cid = contentsService.publish(contents);
	}

}
