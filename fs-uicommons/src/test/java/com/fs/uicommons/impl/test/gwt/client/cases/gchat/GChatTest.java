/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.gchat;

import java.util.List;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatJoinEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatMessageEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.event.BeforeClientStartEvent;

/**
 * @author wu
 * 
 */
public class GChatTest extends TestBase {

	private String GROUPID = "group-001";

	private String TEXT = "text to send";

	private GChatControlI control;

	public void testGroupChat() {

		this.finishing.add("join");
		this.finishing.add("message");

		this.delayTestFinish(this.timeoutMillis * 100);

	}

	@Override
	protected void beforeClientStart(BeforeClientStartEvent e) {

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof GChatConnectedEvent) {
			control = this.manager.getControl(GChatControlI.class, true);
			ChatGroupViewI v = control.openChatgroup(GROUPID);
			new ActionEvent(v, Actions.A_GCHAT_JOIN).property("groupId", GROUPID).dispatch();

		} else if (e instanceof GChatJoinEvent) {
			this.onJoinEvent((GChatJoinEvent) e);
		} else if (e instanceof GChatMessageEvent) {
			this.onMessage((GChatMessageEvent) e);
		}

	}

	/**
	 * @param t
	 */
	protected void onMessage(GChatMessageEvent me) {
		String gid = me.getGroupId();
		ChatGroupModel group = this.control.getOrCreateGroup(gid);
		List<MessageModel> mL = group.getMessageModelList();
		assertEquals("should only one message for now", 1, mL.size());
		MessageModel mm = mL.get(0);

		assertEquals("groupId not correct", GROUPID, gid);
		String pid = mm.getParticipantId();

		GChatControlI gc = this.manager.getControl(GChatControlI.class, true);
		ChatGroupModel gm = gc.getOrCreateGroup(gid);
		List<MessageModel> ml = gm.getMessageModelList();
		assertEquals("message list size issue", 1, ml.size());
		String text = mm.getText();
		assertEquals("message content issue", TEXT, text);
		this.tryFinish("message");
	}

	/**
	 * Dec 23, 2012
	 */
	protected void onJoinEvent(GChatJoinEvent e) {

		String gid = e.getGroupId();
		String pid = e.getParticipantId();

		ChatGroupModel group = this.control.getOrCreateGroup(gid);
		assertEquals("join event recevied with a different gid.", GROUPID, gid);

		ParticipantModel pm = group.getParticipant(pid, false);
		assertNotNull("no participant with id:" + pid, pm);
		this.tryFinish("join");
		// send message

		GChatControlI gc = this.manager.getControl(GChatControlI.class, true);
		gc.send(gid, TEXT);
	}

}
