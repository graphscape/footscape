/**
 *  Dec 24, 2012
 */
package com.fs.uicore.api.gwt.client.message;

import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected EndpointMessageEvent messageData;

	public Throwable getException() {
		return exception;
	}

	public EndpointMessageEvent getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, EndpointMessageEvent md) {
		this.exception = t;
		this.messageData = md;
	}
}
