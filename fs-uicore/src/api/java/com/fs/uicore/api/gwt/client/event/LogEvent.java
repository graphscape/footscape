/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class LogEvent extends Event {

	public static final Event.Type<LogEvent> TYPE = new Event.Type<LogEvent>();

	private String message;

	private UiLoggerI logger;

	/**
	 * @param type
	 */
	public LogEvent(UiLoggerI logger, String msg, UiObjectI src) {
		super(TYPE, src);
		this.logger = logger;
		this.message = msg;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}