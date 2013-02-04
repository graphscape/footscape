/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat.handler;

import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatMessageEvent;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.MessageMW;
import com.fs.uicommons.impl.gwt.client.gchat.AbstractGChatMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class MessageGMH extends AbstractGChatMH<MessageMW> {

	/**
	 * @param gcc
	 */
	public MessageGMH(ContainerI c) {
		super(c);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void handle(MessageMW mw) {
		String gid = mw.getGroupId();
		String pid = mw.getParticipantId();

		MessageModel mm = new MessageModel("message", mw.getTarget());//

		this.getGChatControl().addMessage(mm);

		new GChatMessageEvent(this.getGChatControl(), gid, pid).dispatch();
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected MessageMW wrap(MessageData msg) {
		//
		return new MessageMW(msg);
	}

}
