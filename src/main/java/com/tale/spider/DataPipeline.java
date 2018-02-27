package com.tale.spider;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.tale.model.entity.Contents;
import com.tale.service.ContentsService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Bean
public class DataPipeline implements Pipeline{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Inject
    private ContentsService contentsService;
	public static final String URL_LIST = "https://www.dy2018.com/html/gndy/dyzz/index.*";
	 

    public static final String URL_POST = "https://www.dy2018.com/i/\\d+\\.html";
    public static final String URL_POST2 = "https://www.dy2018.com/html/gndy/dyzz/\\d+/\\d+.html";
    @Override
	public void process(ResultItems resultItems, Task task) {
        try {
      	  Contents contents = new Contents();
      	  contents.setAuthorId(2);
      	  contents.setFmtType("html");
      	  contents.setType("post");
      	  contents.setStatus("publish");
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    System.out.println(entry.getKey() + ":");
                    for (Object o : value) {
                    	System.out.println(o);
                    }
                } else {
                	  String key = entry.getKey();
                	  Object value = entry.getValue();
                	  if("categories".equals(key)) {
                		  contents.setCategories(value.toString());
                	  }else if("content".equals(key)) {
                		  contents.setContent(value.toString());
                	  }else if("created".equals(key)) {
                		  if(value==null)value="0";
                		  contents.setCreated(Integer.parseInt(value.toString()));
                	  }else if("tabs".equals(key)) {
                		  if(value==null) value="";
                		  contents.setTags(value.toString());
                	  }else if("title".equals(key)) {
                		  if(value == null || StringUtils.isBlank(value.toString())) {
                			  throw new Exception("标题为空："+resultItems.getRequest().getUrl()+contents);
                		  }
                		  contents.setTitle(value.toString());
                	  }
                }
            }
            Long cid = contentsService.publish(contents);
            if(cid==null) throw new Exception("保存失败："+resultItems.getRequest().getUrl()+contents);
        } catch (Exception e) {
            logger.error("Pipeline错误", e);
        }
	}

}
