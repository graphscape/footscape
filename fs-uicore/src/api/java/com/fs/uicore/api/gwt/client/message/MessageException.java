/**
 *  Dec 24, 2012
 */
package com.fs.uicore.api.gwt.client.message;

import com.fs.uicore.api.gwt.client.MsgWrapper;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected MsgWrapper messageData;

	public Throwable getException() {
		return exception;
	}

	public MsgWrapper getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, MsgWrapper md) {
		this.exception = t;
		this.messageData = md;
	}
}
