/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class MessageEvent extends Event {

	public static final Type<MessageEvent> TYPE = new Type<MessageEvent>();

	private MessageData message;

	/**
	 * @param type
	 */
	public MessageEvent(Type<? extends MessageEvent> type, EndPointI src, MessageData md) {
		super(type, src);
		this.message = md;
	}

	/**
	 * @return the message
	 */
	public MessageData getMessage() {
		return message;
	}

}
