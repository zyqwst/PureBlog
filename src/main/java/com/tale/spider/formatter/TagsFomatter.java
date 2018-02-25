package com.tale.spider.formatter;

import us.codecraft.webmagic.model.formatter.ObjectFormatter;

public class TagsFomatter implements ObjectFormatter<String> {

	    private String template;
	    
	    @Override
	    public String format(String raw) throws Exception {
	    	int size = Integer.parseInt(template);
	        return raw.replaceAll("\\s*", "").substring(size).replace("/", ",");
	    }

	    @Override
	    public Class<String> clazz() {
	        return String.class;
	    }

	    @Override
	    public void initParam(String[] extra) {
	        template = extra[0];
	    }
	}