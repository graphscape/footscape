/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.gchat;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.gchat.ParticipantManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatGroupGd;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;

/**
 * @author wuzhen
 * 
 */
public class ChatGroupManagerImpl extends EntityGdManagerSupport<ChatGroupGd>
		implements ChatGroupManagerI {

	protected ParticipantManagerI participantManager;

	protected TerminalManagerI terminalManager;

	/**
	 * @param name
	 * @param wcls
	 */
	public ChatGroupManagerImpl() {
		super("chatgroup", ChatGroupGd.class);
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.terminalManager = this.facade
				.getEntityManager(TerminalManagerI.class);
		this.participantManager = this.facade
				.getEntityManager(ParticipantManagerI.class);
	}

	@Override
	public ChatGroupGd createChatRoom(String id, PropertiesI<Object> pts) {
		ChatGroupGd da = new ChatGroupGd(id, pts);
		this.addEntity(da);
		return da;
	}

	@Override
	public ChatGroupGd getChatGroup(String id) {
		return this.getEntity(id);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public ChatGroupGd getChatGroup(String id, boolean force) {
		//
		return this.getEntity(id, force);

	}

	/**
	 * Dec 19, 2012
	 * <p>
	 * only for online participants
	 */

	/*
	 * Dec 19, 2012
	 */
	@Override
	public List<ParticipantGd> getParticipantList(String gid) {
		//
		List<String> rtL = this.getChatGroup(gid).getParticipantIdList();

		return this.participantManager.getEntityList(rtL);
	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public void addParticipant(ParticipantGd rt) {
		//
		// TODO lock
		// TODO duplicated check.
		String gid = rt.getGroupId();
		String pid = rt.getId();
		this.participantManager.addEntity(rt);

		ChatGroupGd cg = this.getChatGroup(gid, true);
		cg.addParticipantId(pid);

		this.addEntity(cg);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.gchat.ChatGroupManagerI#removeParticipant
	 * (java.lang.String)
	 */
	@Override
	public ParticipantGd removeParticipant(String gid, String pid) {
		ChatGroupGd cg = this.getChatGroup(gid, true);
		cg.removeParticipantId(pid);

		this.addEntity(cg);// save update.

		ParticipantGd rt = this.participantManager.removeEntity(pid);

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.gchat.ChatGroupManagerI#getParticipantManager
	 * ()
	 */
	@Override
	public ParticipantManagerI getParticipantManager() {

		return this.participantManager;
	}

}
