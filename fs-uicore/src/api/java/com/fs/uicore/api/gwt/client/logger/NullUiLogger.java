/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 11, 2012
 */
package com.fs.uicore.api.gwt.client.logger;

import com.fs.uicore.api.gwt.client.support.UiLoggerSupport;

/**
 * @author wu
 * 
 */
public class NullUiLogger extends UiLoggerSupport {

	/**
	 * @param level
	 * @param cls
	 */
	public NullUiLogger(String name) {
		super(name);
	}

	@Override
	public boolean isLevelEnabled(int level) {
		return false;
	}

	@Override
	public void log(int level, Object msg) {
		// do nothing.
	}

	/*
	 * Nov 11, 2012
	 */
	@Override
	protected void doLog(int level, Object msg, Throwable t) {
		//

	}

}
