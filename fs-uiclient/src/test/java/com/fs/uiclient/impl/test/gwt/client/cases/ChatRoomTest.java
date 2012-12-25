/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.achat.ChatActivityModel;
import com.fs.uiclient.api.gwt.client.achat.PeerModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.gwt.client.achat.AChatControlImpl;
import com.fs.uiclient.impl.test.gwt.client.cases.support.ActivityTestBase;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu This test not automatic,start test,wait 20-30sec,login in xmpp
 *         server with acc-002 by Client software(such as PSI). Join groupchat
 *         ar-act-001@muc.thinkpad
 * 
 */
public class ChatRoomTest extends ActivityTestBase {

	private ActivityModelI activityModel;

	@Before
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		this.finishing.add("activity.open");
		this.finishing.add("gchat.ready");
		this.finishing.add("chat.room");
		this.finishing.add("exp2.online");
		this.finishing.add("hello.exp2");
		this.finishing.add("exp2.offline");
		this.finishing.add("done");//

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
		this.tryOpenChatRoom();

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

	protected void tryOpenChatRoom() {
		if (this.finishing.contains("activity.open") || this.finishing.contains("gchat.ready")) {
			// wait the two event ,both the activity is open and the xmpp is
			// ready.
			return;

		}
		// open chat room
		// wait the occupant join.

		AChatModel csm = this.rootModel.find(AChatModel.class, true);
		csm.addHandler(ModelChildEvent.TYPE, new EventHandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				//
				if (!e.isAdd() || !(e.getChild() instanceof ChatActivityModel)) {
					return;
				}
				ChatActivityModel cm = (ChatActivityModel) e.getChild();

				ChatRoomTest.this.onChatRoomAdded(ChatRoomTest.this.activityModel.getActivityId(), cm);
			}
		});

		// click the open chat button to join into or create the room for the
		// activity.

		ControlUtil.triggerAction(this.activityModel, ActivityModelI.A_OPEN_CHAT_ROOM);

	}

	/**
	 * Oct 22, 2012
	 */
	protected void onChatRoomAdded(String expectedActId, final ChatActivityModel cm) {
		String rnameActural = cm.getGroupId();
		String rnameExpected = AChatControlImpl.convertActivityId2RoomName(expectedActId);
		assertEquals("room name not correct", rnameExpected, rnameActural);
		this.tryFinish("chat.room");
		// cm.addValueHandler(ChatActivityModel.L_LAST_PEER_MODEL_OF_PRESENCE_CHANGED,
		// new EventHandlerI<ModelValueEvent>() {
		//
		// @Override
		// public void handle(ModelValueEvent e) {
		// ChatRoomTest.this.onPeerPresence(e);
		// }
		// });
		// cm.addValueHandler(ChatActivityModel.L_LAST_MESSAGE_MODEL, new
		// EventHandlerI<ModelValueEvent>() {
		//
		// @Override
		// public void handle(ModelValueEvent e) {
		// ChatRoomTest.this.onMessage(cm, e);
		// }
		// });
	}

	protected void onMessage(ChatActivityModel cm, ModelValueEvent e) {
		// echo to message
		MessageModel mm = (MessageModel) e.getValue();

		String body = mm.getText();

		if (body.startsWith("autoresponse:")) {
			// ignore,for loop
		} else {
			String actId = cm.getActivityId();

			String msg = "autoresponse:" + "hello,message got:(" + body + "),from:" + mm.getParticipantId()
					+ ",actId:" + actId + ",waiting:" + this.finishing;

			this.send(actId, msg);
			//
			if (body.startsWith("finish:")) {
				String f = body.substring("finish:".length());
				this.tryFinish(f);
				this.send(actId, "autoresponse:finish:" + f + ",waiting:" + this.finishing);
			}

		}

	}

	private void send(String actId, String msg) {
		AChatControlI cms = this.manager.getControl(AChatControlI.class, true);
		cms.send(actId,msg);//
	}

	protected void onPeerPresence(ModelValueEvent e) {
		PeerModel pm = (PeerModel) e.getValue();

		String actId = pm.getChatRoom().getActivityId();

		String msg = "hi,xmppUser:" + pm.getXmppUser() + ",expId:" + pm.getExpId() + ",pre:"
				+ pm.getPresence();
		msg += "\n this is testing, waiting:" + this.finishing;
		this.send(actId, msg);

		// TODO sendd message
		if ("online".equals(pm.getPresence())) {
			this.tryFinish("exp2.online");
			// todo message

			this.tryFinish("hello.exp2");
		} else if ("offline".equals(pm.getPresence())) {
			this.tryFinish("exp2.offline");
		} else {
			System.out.println("TODO presence:" + pm.getPresence());
		}
	}

	/*
	 * Dec 25, 2012
	 */
	@Override
	protected void beforeAuth(SessionModelI sm) {
		//

	}

}
