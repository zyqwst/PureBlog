package com.tale.spider.formatter;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.model.formatter.ObjectFormatter;

public class TimestampFomatter implements ObjectFormatter<Integer> {

	    private String template;
	    
		public TimestampFomatter() {
		}
	    public TimestampFomatter(String template) {
	    		this.template = template;
	    }
	    @Override
	    public Integer format(String raw) throws Exception {
	    	if(raw==null || StringUtils.isBlank(raw)) return null;
	    	SimpleDateFormat sdf = new SimpleDateFormat(template);
	    	String dateStr = raw.replace("发布时间：", "").trim();
	    	Long s = sdf.parse(dateStr).getTime()/1000;
	        return s.intValue();
	    }

	    @Override
	    public Class<Integer> clazz() {
	        return Integer.class;
	    }

	    @Override
	    public void initParam(String[] extra) {
	        template = extra[0];
	    }
	}