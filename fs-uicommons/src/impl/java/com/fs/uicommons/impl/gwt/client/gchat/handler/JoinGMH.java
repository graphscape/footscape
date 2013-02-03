/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat.handler;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.JoinMW;
import com.fs.uicommons.impl.gwt.client.gchat.AbstractGChatMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class JoinGMH extends AbstractGChatMH<JoinMW> {

	/**
	 * @param gcc
	 */
	public JoinGMH(ContainerI gcc) {
		super(gcc);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(JoinMW mw) {
		String gid = mw.getGroupId();
		String pid = mw.getJoinParticipantId();
		String accId = mw.getAccountId();

		ChatGroupModel group = new ChatGroupModel(gid);
		ParticipantModel p = new ParticipantModel(pid, gid);
		p.setAccountId(accId);
		p.setNick(accId);// TODO nick
		p.setRole(mw.getRole());

		this.getGChatControl().addParticipant(p);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected JoinMW wrap(MessageData msg) {
		//
		return new JoinMW(msg);
	}

}
