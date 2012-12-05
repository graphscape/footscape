/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory.Configuration;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public abstract class UiLoggerSupport implements UiLoggerI {

	protected String name;

	protected static Map<Integer, String> levelName = new HashMap<Integer, String>();

	static {
		levelName.put(UiLoggerI.LEVEL_DEBUG, "DEBUG");
		levelName.put(UiLoggerI.LEVEL_INFO, "INFO");
		levelName.put(UiLoggerI.LEVEL_WARN, "ERROR");
		levelName.put(UiLoggerI.LEVEL_ERROR, "ERROR");

	}

	public UiLoggerSupport(String name2) {
		this.name = name2;
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

		return this.getLevel() >= level;
	}

	public int getLevel() {
		Configuration cfg = UiLoggerFactory.getConfiguration4Logger(this.name);
		return cfg.getLoggerLevel();
	}

	public void log(int level, Object msg) {
		log(level, msg, null);
	}

	public void log(int level, Object msg, Throwable t) {
		if (!this.isLevelEnabled(level)) {
			return;// ignore

		}
		this.doLog(level, msg, t);
	}

	protected abstract void doLog(int level, Object msg, Throwable t);

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
