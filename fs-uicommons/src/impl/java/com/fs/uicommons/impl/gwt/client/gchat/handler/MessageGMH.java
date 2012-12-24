/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat.handler;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.MessageMW;
import com.fs.uicommons.impl.gwt.client.gchat.AbstractGChatMH;
import com.fs.uicommons.impl.gwt.client.gchat.MessageModel;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class MessageGMH extends AbstractGChatMH<MessageMW> {

	/**
	 * @param gcc
	 */
	public MessageGMH(GChatControlI gcc) {
		super(gcc);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void handle(MessageMW mw) {
		String gid = mw.getGroupId();

		GChatModel cm = this.control.getChatModel();
		ChatGroupModel group = cm.getGroup(gid, true);

		MessageModel mm = new MessageModel("message", mw.getTarget());//

		group.addMessage(mm);
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
