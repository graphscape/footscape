/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.dispatcher;

import java.util.regex.Pattern;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.HandlerI;

/**
 * @author wu
 * 
 */
public class HandlerEntry {

	private int priority;

	private Pattern pattern;

	private Pattern exclude;

	private boolean isDefault;

	private HandlerI handler;

	public HandlerEntry(HandlerI h) {
		this.handler = h;
		Configuration c = h.getConfiguration();//
		String ptS = c.getProperty("pattern");
		String exS = c.getProperty("exclude");
		this.pattern = ptS == null ? null : Pattern.compile(ptS);//
		this.exclude = exS == null ? null : Pattern.compile(exS);

		this.isDefault = c.getPropertyAsBoolean("default", false);
		if (this.isDefault && this.pattern != null) {
			throw new FsException("default handler should no pattern");
		}
		this.priority = c.getPropertyAsInt("priority", 0);// TODO
	}

	@Override
	public String toString() {
		return this.priority + ":" + this.priority + ",pattern:" + this.pattern
				+ ",exclude:" + exclude + ",isDefault:" + this.isDefault
				+ ",handler:" + this.handler;
	}

	public boolean isMatch(String path) {
		boolean include = this.pattern == null ? true : this.pattern.matcher(
				path).matches();
		boolean exclude = this.exclude == null ? false : this.exclude.matcher(
				path).matches();
		return include && !exclude;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @return the handler
	 */
	public HandlerI getHandler() {
		if (this.handler != null) {
			return this.handler;
		}

		// this.handler = Configuration.newConfigurable(handlerClass);//
		// TODO
		return this.handler;
	}

	/**
	 * @return the isDefault
	 */
	public boolean isDefault() {
		return isDefault;
	}

}
