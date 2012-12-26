/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat.handler;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatJoinEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatYouJoinEvent;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.JoinMW;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicommons.impl.gwt.client.gchat.AbstractGChatMH;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class JoinGMH extends AbstractGChatMH<JoinMW> {

	/**
	 * @param gcc
	 */
	public JoinGMH(GChatControlI gcc) {
		super(gcc);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void handle(JoinMW mw) {
		String gid = mw.getGroupId();
		String pid = mw.getJoinParticipantId();
		String accId = mw.getAccountId();
		GChatModel cm = this.control.getChatModel();
		ChatGroupModel group = cm.getGroup(gid, true);//
		ParticipantModel p = new ParticipantModel(pid);
		p.setAccountId(accId);
		p.setNick(accId);// TODO nick
		p.setRole(mw.getRole());
		group.addParticipant(p);

		new GChatJoinEvent(this.control, gid, pid).dispatch();//

		SessionModelI sm = this.control.getSessionModel(true);
		if (sm.isAccount(accId)) {
			new GChatYouJoinEvent(this.control, gid, pid).dispatch();
		}

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
