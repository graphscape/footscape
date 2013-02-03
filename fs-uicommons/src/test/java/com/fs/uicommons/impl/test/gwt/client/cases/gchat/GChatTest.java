/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.gchat;

import java.util.List;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.BeforeClientStartEvent;

/**
 * @author wu
 * 
 */
public class GChatTest extends TestBase {

	private String GROUPID = "group-001";

	private String TEXT = "text to send";

	public void testGroupChat() {

		this.finishing.add("join");
		this.finishing.add("message");

		this.delayTestFinish(this.timeoutMillis * 100);

	}

	protected void beforeClientStart(BeforeClientStartEvent e) {

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof AttachedEvent) {
			Object src = e.getSource();
			if (src instanceof GChatControlI) {
				this.onGChatAttached();
			}

		}
	}

	/**
	 * Dec 23, 2012
	 */
	private void onGChatAttached() {
		GChatControlI cc = this.client.find(GChatControlI.class, true);

		// cc.addHandler(GChatJoinEvent.TYPE, new
		// EventHandlerI<GChatJoinEvent>() {
		//
		// @Override
		// public void handle(GChatJoinEvent t) {
		// GChatTest.this.onJoinEvent(t);
		// }
		// });

		cc.addHandler(GChatConnectEvent.TYPE, new EventHandlerI<GChatConnectEvent>() {

			@Override
			public void handle(GChatConnectEvent t) {
				GChatTest.this.onGChatConnect(t);
			}
		});

		// cc.addHandler(GChatMessageEvent.TYPE, new
		// EventHandlerI<GChatMessageEvent>() {
		//
		// @Override
		// public void handle(GChatMessageEvent t) {
		// GChatTest.this.onMessage(t);
		// }
		// });
		if (cc.isConnected()) {
			this.join();//
		}
	}

	/**
	 * @param t
	 */
	protected void onMessage(MessageModel mm) {
		String gid = mm.getGroupId();
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

	protected void onGChatConnect(GChatConnectEvent t) {
		if (!t.isConnected()) {
			return;//
		}

		this.join();
	}

	protected void join() {
		GChatControlI cc = this.client.find(GChatControlI.class, true);
		cc.join(GROUPID);
	}

	/**
	 * Dec 23, 2012
	 */
	protected void onJoinEvent(ParticipantModel t) {
		System.out.println("joinevent:" + t);
		String gid = t.getGroupId();
		String pid = t.getId();
		assertEquals("join event recevied with a different gid.", GROUPID, gid);
		this.tryFinish("join");
		// send message

		GChatControlI gc = this.manager.getControl(GChatControlI.class, true);
		gc.send(gid, TEXT);
	}

}
