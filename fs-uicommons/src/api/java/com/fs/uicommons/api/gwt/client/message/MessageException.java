/**
 *  Dec 24, 2012
 */
package com.fs.uicommons.api.gwt.client.message;

import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected MessageData messageData;

	public Throwable getException() {
		return exception;
	}

	public MessageData getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, MessageData md) {
		this.exception = t;
		this.messageData = md;
	}
}
