/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.chat;

import java.util.List;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.api.chat.ChatRoomI;
import com.fs.gridservice.commons.api.chat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.chat.data.ParticipantGd;
import com.fs.gridservice.commons.api.chat.data.PresenceGd;
import com.fs.gridservice.commons.api.chat.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;

/**
 * @author wuzhen
 * 
 */
public class ChatRoomImpl implements ChatRoomI {

	protected String chatRoomId;

	protected ChatRoomManagerImpl manager;

	/**
	 * @param id
	 * @param chatRoomManagerImpl
	 */
	public ChatRoomImpl(String id, ChatRoomManagerImpl manager) {
		this.chatRoomId = id;
		this.manager = manager;
	}

	@Override
	public void dispatch(ChatMessageGd cm) {
		// send to web socket

		String wsId = null;

		MessageI msg = cm.getTarget();//

		WsMsgSendEW wsm = WsMsgSendEW.valueOf("", wsId, msg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.api.chat.ChatRoomI#getParticipantList()
	 */
	@Override
	public List<ParticipantGd> getParticipantList() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.chat.ChatRoomI#getTerminalByParticipantId
	 * (java.lang.String)
	 */
	@Override
	public TerminalGd getTerminalByParticipantId(String ptId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.chat.ChatRoomI#addParticipant(java.lang
	 * .String)
	 */
	@Override
	public void addParticipant(String ptId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.chat.ChatRoomI#presence(com.fs.gridservice
	 * .commons.api.chat.data.PresenceGd)
	 */
	@Override
	public void presence(PresenceGd pp) {
		// TODO Auto-generated method stub

	}

}
