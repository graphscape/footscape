/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.GChatMW;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * @deprecated
 */
public abstract class AbstractGChatMH<T extends GChatMW> extends UiHandlerSupport implements
		MessageHandlerI<MsgWrapper> {

	public AbstractGChatMH(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(MsgWrapper evt) {
		//
		MessageData msg = evt.getMessage();
		T mw = this.wrap(msg);
		this.handle(mw);
	}
	protected GChatControlI getGChatControl(){
		return this.getControl(GChatControlI.class, true);
	}
	
	protected UserInfo getUserInfo() {
		
		return null;//TODO this.getClient(true).getEndpoint().getUserInfo();
	}

	protected abstract T wrap(MessageData msg);

	protected abstract void handle(T mw);

}
