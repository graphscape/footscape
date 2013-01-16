/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.CollectionTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.LoginTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.TestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.signup.ActivityTest;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatGroupCreatedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatMessageEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatYouJoinEvent;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.MessageMW;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;

/**
 * @author wu This test not automatic,start test,wait 20-30sec,login in xmpp
 *         server with acc-002 by Client software(such as PSI). Join groupchat
 *         ar-act-001@muc.thinkpad
 * 
 */
public class AChatTest extends ActivityTest {

	private static String TEXT = "hello exp2";

	private ActivityModelI activityModel;
	private ActivityView activityView;
	private TestWorker worker;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		this.finishing.add("activity.open");
		this.finishing.add("gchat.ready");
		this.finishing.add("open.group");
		this.finishing.add("group.created");
		this.finishing.add("group.youjoin");
		this.finishing.add("hello.exp2");

	}

	public void testAChat() {
		CollectionTestWorker ctw = new CollectionTestWorker();
		ctw.addTestWorker(new LoginTestWorker("user1", "user1@some.com", "user1"));
		ctw.addTestWorker(new ExpTestWorker(3));
		ctw.addTestWorker(new ActivityTestWorker());

		this.worker = ctw;

		this.delayTestFinish(this.timeoutMillis * 100);

	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof GChatConnectEvent) {
			GChatConnectEvent ce = (GChatConnectEvent) e;
			this.onGChatConnect(ce);

		} else if (e instanceof GChatGroupCreatedEvent) {
			this.onChatGroupCreated((GChatGroupCreatedEvent) e);
		} else if (e instanceof GChatMessageEvent) {
			this.onGChatMessage((GChatMessageEvent) e);

		} else if (e instanceof GChatYouJoinEvent) {
			this.onYouJoin((GChatYouJoinEvent) e);
		}
	}

	public void onGChatConnect(GChatConnectEvent e) {
		e.getGChat();

		this.tryFinish("gchat.ready");
		this.tryOpenChatGroup();

		// listen to the anthed and activity open

	}

	/*
	 * Jan 12, 2013
	 */
	@Override
	protected void onAttachedEvent(AttachedEvent ae) {
		//
		super.onAttachedEvent(ae);
		UiObjectI src = ae.getSource();
		if (src instanceof ActivityView) {
			this.onActivityViewAttached((ActivityView) src);
		}
	}

	/*
	 * Oct 22, 2012
	 */
	protected void onActivityViewAttached(ActivityView src) {
		this.activityView = src;
		this.activityModel = (ActivityModelI) src.getModel();// for
																// chat
																// room
																// to
		// be open.
		this.tryFinish("activity.open");
		this.tryOpenChatGroup();

	}

	protected void tryOpenChatGroup() {
		if (this.finishing.contains("activity.open") || this.finishing.contains("gchat.ready")) {
			// wait the two event ,both the activity is open and the gchat is
			// ready.
			return;

		}
		// open chat room
		// wait the occupant join.

		// click the open chat button to join into or create the room for the
		// activity.
		// open from activity.
		this.activityView.clickAction(Actions.A_ACT_OPEN_CHAT_ROOM);
		this.tryFinish("open.group");
	}

	/**
	 * Oct 22, 2012
	 */
	protected void onChatGroupCreated(GChatGroupCreatedEvent e) {

		String gid = e.getGroupId();
		String actId = this.activityModel.getActivityId();
		assertEquals("room name not correct", actId, gid);//

		this.tryFinish("group.created");

	}

	/**
	 * @param e
	 */
	private void onYouJoin(GChatYouJoinEvent e) {
		this.tryFinish("group.youjoin");
		String gid = e.getGroupId();//
		GChatControlI cms = this.manager.getControl(GChatControlI.class, true);
		cms.send(gid, TEXT);//

	}

	protected void onGChatMessage(GChatMessageEvent e) {
		// echo to message
		MessageMW mw = e.getGChatMessage();
		MessageData md = (MessageData) mw.getTarget().getPayload("message");

		String body = md.getString("text", true);
		assertEquals(TEXT, body);
		this.tryFinish("hello.exp2");
	}

}
