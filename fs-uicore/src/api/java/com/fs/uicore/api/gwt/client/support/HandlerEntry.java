/**
 *  Dec 24, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author wuzhen
 * 
 */
public class HandlerEntry {

	protected Path path;

	protected MessageHandlerI handlers;

	protected boolean strict;

	public HandlerEntry(Path p, boolean includeSubPath, MessageHandlerI hdls) {
		this.path = p;
		this.strict = includeSubPath;
		this.handlers = hdls;
	}

	public boolean tryHandle(boolean dely, Path p, final MsgWrapper md) {
		if (!this.isMatch(p)) {
			return false;
		}
		if (dely) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					HandlerEntry.this.handlers.handle(md);

				}
			});
		} else {
			this.handlers.handle(md);
		}
		return true;
	}

	public boolean isMatch(Path p) {
		if (this.strict) {
			return this.path.equals(p);//
		} else {
			return this.path.isSubPath(p, true);
		}
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public String toString() {
		return "{path:" + this.path + ",handler:" + this.handlers + "}";
	}

}
