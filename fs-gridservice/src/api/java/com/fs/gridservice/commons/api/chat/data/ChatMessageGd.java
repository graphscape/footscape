/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.chat.data;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.core.api.gdata.MessageGd;

/**
 * @author wuzhen
 * 
 */
public class ChatMessageGd extends MessageGd {

	public static final String PK_FROM = "_fromParticipantId";

	public static final String PK_CHATROOMID = "_chatRoomId";

	public ChatMessageGd() {

	}

	public ChatMessageGd(MessageI msg) {
		super(msg);
	}

	public String getFrom() {
		return (String) this.getHeader(PK_FROM);
	}

	public String getChatRoomId() {
		return (String) this.getHeader(PK_CHATROOMID);
	}

}