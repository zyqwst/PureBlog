package com.tale.spider.formatter;

import us.codecraft.webmagic.model.formatter.ObjectFormatter;

public class CategoriesFomatter implements ObjectFormatter<String> {

	    
	    @Override
	    public String format(String raw) throws Exception {
		    	try {
					String s = raw.replaceAll("\\s*", "");
					if(s.contains("香港")||s.contains("中国") || s.contains("台湾")) {
						return "国内电影";
					}else if(s.contains("日本") || s.contains("韩国")) {
						return "日韩电影";
					}else {
						return "欧美电影";
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(e);
				}
	    }

	    @Override
	    public Class<String> clazz() {
	        return String.class;
	    }

	    @Override
	    public void initParam(String[] extra) {
	    }
	}