/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.terminal.event;

import com.fs.uicommons.api.gwt.client.terminal.TerminalI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class MessageEvent extends Event {

	public static final Type<MessageEvent> TYPE = new Type<MessageEvent>();

	protected MessageData message;

	/**
	 * @param tem
	 * @param type
	 */
	public MessageEvent(TerminalI tem, MessageData msg) {
		super(TYPE, tem);
		this.message = msg;
	}

	/**
	 * @return the message
	 */
	public MessageData getMessage() {
		return message;
	}

	public String getPath() {
		return this.message.getHeader("path", true);
	}

}
