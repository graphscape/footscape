/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases;

import org.junit.Before;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomControlI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatroom.PeerModel;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsControlI;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsModelI;
import com.fs.uiclient.impl.gwt.client.chatrooms.ChatRoomsControl;
import com.fs.uiclient.impl.test.gwt.client.cases.support.ActivityTestBase;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;

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
		this.finishing.add("xmpp.ready");
		this.finishing.add("chat.room");
		this.finishing.add("exp2.online");
		this.finishing.add("hello.exp2");
		this.finishing.add("exp2.offline");
		this.finishing.add("done");//

	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	protected void beforeAuth(SessionModelI sm) {

		// sm.setValue(SessionModelI.L_DOMAIN, "thinkpad");// TODO configurable.

		// sm.setValue(SessionModelI.L_XMPP_USER, this.account);

		// sm.setValue(SessionModelI.L_XMPP_PASSWORD, this.account);

	}

	public void testActivityChat() {
		// listen to the xmpp ready.
		XmppModelI xm = this.rootModel.find(XmppModelI.class, true);//
		xm.addValueHandler(XmppModelI.L_RESOURCE_BAND, Boolean.TRUE,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ChatRoomTest.this.tryFinish("xmpp.ready");
						ChatRoomTest.this.tryOpenChatRoom();
					}
				});
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
		if (this.finishing.contains("activity.open")
				|| this.finishing.contains("xmpp.ready")) {
			// wait the two event ,both the activity is open and the xmpp is
			// ready.
			return;

		}
		// open chat room
		// wait the occupant join.

		ChatRoomsModelI csm = this.rootModel.find(ChatRoomsModelI.class, true);
		csm.addHandler(ModelChildEvent.TYPE,
				new EventHandlerI<ModelChildEvent>() {

					@Override
					public void handle(ModelChildEvent e) {
						//
						if (!e.isAdd()
								|| !(e.getChild() instanceof ChatRoomModelI)) {
							return;
						}
						ChatRoomModelI cm = (ChatRoomModelI) e.getChild();

						ChatRoomTest.this.onChatRoomAdded(
								ChatRoomTest.this.activityModel.getActivityId(),
								cm);
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
	protected void onChatRoomAdded(String expectedActId, final ChatRoomModelI cm) {
		String rnameActural = cm.getRoomName();
		String rnameExpected = ChatRoomsControl
				.convertActivityId2RoomName(expectedActId);
		assertEquals("room name not correct", rnameExpected, rnameActural);
		this.tryFinish("chat.room");
		cm.addValueHandler(
				ChatRoomModelI.L_LAST_PEER_MODEL_OF_PRESENCE_CHANGED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ChatRoomTest.this.onPeerPresence(e);
					}
				});
		cm.addValueHandler(ChatRoomModelI.L_LAST_MESSAGE_MODEL,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ChatRoomTest.this.onMessage(cm, e);
					}
				});
	}

	protected void onMessage(ChatRoomModelI cm, ModelValueEvent e) {
		// echo to message
		MessageModel mm = (MessageModel) e.getValue();

		String body = mm.getBody();

		if (body.startsWith("autoresponse:")) {
			// ignore,for loop
		} else {
			String actId = cm.getActivityId();

			XmppModelI xmpp = this.rootModel.find(XmppModelI.class, true);//
			String msg = "autoresponse:" + "hello,message got:(" + body
					+ "),from:" + mm.getFrom() + ",to:" + mm.getTo() + ",me:"
					+ xmpp.getJid() + ",actId:" + actId + ",waiting:"
					+ this.finishing;

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
		ChatRoomsControlI cms = this.manager.getControl(
				ChatRoomsControlI.class, true);
		ChatRoomControlI cc = cms.getChatRoomControl(actId, true);
		cc.send(msg);//
	}

	protected void onPeerPresence(ModelValueEvent e) {
		PeerModel pm = (PeerModel) e.getValue();

		String actId = pm.getChatRoom().getActivityId();

		String msg = "hi,xmppUser:" + pm.getXmppUser() + ",expId:"
				+ pm.getExpId() + ",pre:" + pm.getPresence();
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

}
