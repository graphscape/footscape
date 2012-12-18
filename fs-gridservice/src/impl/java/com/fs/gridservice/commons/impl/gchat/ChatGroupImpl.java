/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.gchat;

import java.util.List;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.api.gchat.ChatGroupI;
import com.fs.gridservice.commons.api.gchat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;
import com.fs.gridservice.commons.api.gchat.data.PresenceGd;
import com.fs.gridservice.commons.api.gchat.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;

/**
 * @author wuzhen
 * 
 */
public class ChatGroupImpl implements ChatGroupI {

	protected String groupId;

	protected ChatGroupManagerImpl manager;

	/**
	 * @param id
	 * @param chatRoomManagerImpl
	 */
	public ChatGroupImpl(String id, ChatGroupManagerImpl manager) {
		this.groupId = id;
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
	 * @see com.fs.gridservice.commons.api.gchat.ChatGroupI#getParticipantList()
	 */
	@Override
	public List<ParticipantGd> getParticipantList() {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.gchat.ChatGroupI#getTerminalByParticipantId
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
	 * com.fs.gridservice.commons.api.gchat.ChatGroupI#addParticipant(java.lang
	 * .String)
	 */
	@Override
	public void addParticipant(String ptId) {
		//TODO dispatch 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.gchat.ChatGroupI#presence(com.fs.gridservice
	 * .commons.api.chat.data.PresenceGd)
	 */
	@Override
	public void presence(PresenceGd pp) {
		//TODO dispatch
	}

}
