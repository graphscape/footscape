/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package com.fs.uicore.api.gwt.client.logger;

import java.util.Date;

import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.support.UiLoggerSupport;

/**
 * @author wu
 * 
 */
public class SimpleUiLogger extends UiLoggerSupport {

	public SimpleUiLogger(String name) {
		super(name);
	}

	@Override
	public void doLog(int level, Object msg, Throwable t) {
		String levelS = levelName.get(level);
		String log = "[" + new Date() + "][" + levelS + "][" + this.name + "]-"
				+ msg;
		Console.getInstance().println(log);

		if (t != null) {
			String more = this.getMessage(t);
			Console.getInstance().println("throwable:" + more);//
			
		}

	}
	
	private String getMessage (Throwable throwable) {
	    String ret="";
	    while (throwable!=null) {
	            if (throwable instanceof com.google.gwt.event.shared.UmbrellaException){
	                    for (Throwable thr2 :((com.google.gwt.event.shared.UmbrellaException)throwable).getCauses()){
	                            if (ret != "")
	                                    ret += "\nCaused by: ";
	                            ret += thr2.toString();
	                            ret += "\n  at "+getMessage(thr2);
	                    }
	            } else if (throwable instanceof com.google.web.bindery.event.shared.UmbrellaException){
	                    for (Throwable thr2 :((com.google.web.bindery.event.shared.UmbrellaException)throwable).getCauses()){
	                            if (ret != "")
	                                    ret += "\nCaused by: ";
	                            ret += thr2.toString();
	                            ret += "\n  at "+getMessage(thr2);
	                    }
	            } else {
	                    if (ret != "")
	                            ret += "\nCaused by: ";
	                    ret += throwable.toString();
	                    for (StackTraceElement sTE : throwable.getStackTrace())
	                            ret += "\n  at "+sTE;
	            }
	            throwable = throwable.getCause();
	    }

	    return ret;
	}

}
