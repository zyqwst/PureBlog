package com.tale;

import java.io.PrintWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyFilePipeline implements Pipeline {
	PrintWriter printWriter;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public void setPrint(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}
	

    public MyFilePipeline(PrintWriter printWriter) {
        setPrint(printWriter);
    }

	@Override
	public void process(ResultItems resultItems, Task task) {
        try {
            //printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    printWriter.println(entry.getKey() + ":");
                    for (Object o : value) {
                        printWriter.println(o);
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                }
            }
            printWriter.flush();
        } catch (Exception e) {
            logger.warn("Pipeline错误", e);
        }
	}

}
