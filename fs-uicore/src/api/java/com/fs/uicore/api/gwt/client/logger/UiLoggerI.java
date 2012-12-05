/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicore.api.gwt.client.logger;

/**
 * @author wu
 * 
 */
public interface UiLoggerI {
	

	public static final int LEVEL_DISABLE = -1;
	
	public static final int LEVEL_ERROR = 0;

	public static final int LEVEL_WARN = 10;

	public static final int LEVEL_INFO = 20;

	public static final int LEVEL_DEBUG = 30;
	
	public static final int LEVEL_TRACE = 40;
	
	public String getName();

	public void info(Object msg);

	public void debug(Object msg);

	public void error(Object msg, Throwable t);
	
	public boolean isDebugEnabled();
	
}
