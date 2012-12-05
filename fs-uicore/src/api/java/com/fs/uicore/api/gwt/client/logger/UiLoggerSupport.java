/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package com.fs.uicore.api.gwt.client.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class UiLoggerSupport implements UiLoggerI {

	private String name;

	private int level;

	public UiLoggerSupport(int level, Class cls) {
		this(level, cls.getName());
	}

	public UiLoggerSupport(int level, String name) {
		this.level = level;
		this.name = name;
	}

	public void debug(Object msg) {

		log(UiLoggerI.LEVEL_DEBUG, msg);

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String getName() {
		//
		return name;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public void info(Object msg) {
		log(UiLoggerI.LEVEL_INFO, msg);
	}

	public boolean isLevelEnabled(int level) {
		return this.level >= level;
	}

	public void log(int level, Object msg) {
		log(level, msg, null);
	}

	public void log(int level, Object msg, Throwable t) {
		if (!this.isLevelEnabled(level)) {
			return;// ignore

		}
		String levelS = UiLoggerFactory.getLevelName(level, false);
		String log = "[" + new Date() + "][" + levelS + "][" + this.name + "]-"
				+ msg;
		Console.getInstance().println(log);

		if (t != null) {
			Console.getInstance().println(
					"see stack trace in system out,throwable:" + t);//
			t.printStackTrace();
		}

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public void error(Object msg, Throwable t) {
		log(UiLoggerI.LEVEL_ERROR, msg, t);
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public boolean isDebugEnabled() {
		//
		return this.isLevelEnabled(LEVEL_DEBUG);

	}
}
