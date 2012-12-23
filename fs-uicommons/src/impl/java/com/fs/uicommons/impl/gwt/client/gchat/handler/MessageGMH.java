/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat.handler;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.TextMW;
import com.fs.uicommons.impl.gwt.client.gchat.AbstractGChatMH;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class MessageGMH extends AbstractGChatMH<TextMW> {

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
	protected void handle(TextMW mw) {
		String gid = mw.getGroupId();
		String text = mw.getText();//
				
		GChatModel cm = this.control.getChatModel();
		ChatGroupModel group = cm.getGroup(gid, true);
		
	}

	/*
	 *Dec 23, 2012
	 */
	@Override
	protected TextMW wrap(MessageData msg) {
		// 
		return new TextMW(msg);
	}

}
