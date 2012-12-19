/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper.gchat;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.EventWrapper;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class ChatMsgEW extends EventWrapper {

	public static final EventType TYPE = EventType.valueOf("ChatMsg");

	public static ChatMsgEW valueOf(String path, String tid, MessageI msg) {

		ChatMsgEW rt = new ChatMsgEW(new EventGd(TYPE, path));

		return rt;
	}

	public ChatMsgEW(EventGd target) {
		super(target);
	}

}
