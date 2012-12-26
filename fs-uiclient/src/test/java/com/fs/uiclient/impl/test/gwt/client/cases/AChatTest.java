/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.test.gwt.client.cases.support.ActivityTestBase;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatGroupCreatedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatMessageEvent;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.MessageMW;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu This test not automatic,start test,wait 20-30sec,login in xmpp
 *         server with acc-002 by Client software(such as PSI). Join groupchat
 *         ar-act-001@muc.thinkpad
 * 
 */
public class AChatTest extends ActivityTestBase {

	private ActivityModelI activityModel;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		this.finishing.add("activity.open");
		this.finishing.add("gchat.ready");
		this.finishing.add("group.created");
		this.finishing.add("group.youjoin");
		this.finishing.add("hello.exp2");
		this.finishing.add("done");//

	}
	public void testAChat(){
		
		this.delayTestFinish(this.timeoutMillis*100);
	
	}
	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof GChatConnectEvent) {
			GChatConnectEvent ce = (GChatConnectEvent) e;
			this.onGChatConnect(ce);

		}
	}

	public void onGChatConnect(GChatConnectEvent e) {
		e.getGChat();

		this.tryFinish("gchat.ready");
		this.tryOpenChatGroup();

		// listen to the anthed and activity open
		super.start();

	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	protected void onActivityOpen(final ActivityModelI am) {
		this.activityModel = am;// for chat room to be open.
		this.tryFinish("activity.open");

	}

	protected void tryOpenChatGroup() {
		if (this.finishing.contains("activity.open")
				|| this.finishing.contains("gchat.ready")) {
			// wait the two event ,both the activity is open and the xmpp is
			// ready.
			return;

		}
		// open chat room
		// wait the occupant join.

		// AChatModel csm = this.rootModel.find(AChatModel.class, true);
		GChatControlI gcc = this.rootModel.getClient(true)
				.getChild(ControlManagerI.class, true)
				.getControl(GChatControlI.class, true);
		
		gcc.addHandler(GChatGroupCreatedEvent.TYPE,
				new EventHandlerI<GChatGroupCreatedEvent>() {

					@Override
					public void handle(GChatGroupCreatedEvent e) {

						AChatTest.this.onChatGroupCreated(e);
					}
				});

		// click the open chat button to join into or create the room for the
		// activity.

		ControlUtil.triggerAction(this.activityModel,
				ActivityModelI.A_OPEN_CHAT_ROOM);

	}

	/**
	 * Oct 22, 2012
	 */
	protected void onChatGroupCreated(GChatGroupCreatedEvent e) {
		GChatControlI gc = e.getGChat();

		String gid = e.getGroupId();
		String actId = this.activityModel.getActivityId();
		assertEquals("room name not correct", actId, gid);//

		this.tryFinish("group.created");
		gc.addHandler(GChatMessageEvent.TYPE,
				new EventHandlerI<GChatMessageEvent>() {

					@Override
					public void handle(GChatMessageEvent t) {
						AChatTest.this.onMessage(t);
					}
				});
	}

	protected void onMessage(GChatMessageEvent e) {
		// echo to message
		MessageMW mw = e.getMessage();
		MessageData md = (MessageData) mw.getTarget().getPayload("message");

		String body = md.getString("text", true);

		if (body.startsWith("autoresponse:")) {
			// ignore,for loop
		} else {
			String actId = this.activityModel.getActivityId();

			String msg = "autoresponse:" + "hello,message got:(" + body
					+ "),from:" + e.getParticipantId() + ",actId:" + actId
					+ ",waiting:" + this.finishing;

			this.send(actId, msg);
			//
			if (body.startsWith("finish:")) {
				String f = body.substring("finish:".length());
				this.tryFinish(f);
				this.send(actId, "autoresponse:finish:" + f + ",waiting:"
						+ this.finishing);
			}

		}

	}

	private void send(String actId, String msg) {
		AChatControlI cms = this.manager.getControl(AChatControlI.class, true);
		cms.send(actId, msg);//
	}

	/*
	 * Dec 25, 2012
	 */
	@Override
	protected void beforeAuth(SessionModelI sm) {
		//

	}

}
