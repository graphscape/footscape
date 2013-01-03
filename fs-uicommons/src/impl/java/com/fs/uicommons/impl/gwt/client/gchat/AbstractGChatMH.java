/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.GChatMW;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public abstract class AbstractGChatMH<T extends GChatMW> implements MessageHandlerI {

	protected GChatControlI control;

	public AbstractGChatMH(GChatControlI gcc) {
		this.control = gcc;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(EndpointMessageEvent evt) {
		//
		MessageData msg = evt.getMessage();
		T mw = this.wrap(msg);
		this.handle(mw);
	}

	protected UserInfo getUserInfo() {
		return this.control.getClient(true).getEndpoint().getUserInfo();
	}

	protected abstract T wrap(MessageData msg);

	protected abstract void handle(T mw);

}
