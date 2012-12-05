/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 29, 2012
 */
package com.fs.uixmpp.impl.test.gwt.client.cases;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.core.api.gwt.client.event.ResourceBindSuccessEvent;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;
import com.fs.uixmpp.groupchat.impl.gwt.client.room.RoomView;
import com.fs.uixmpp.groupchat.impl.gwt.client.rooms.RoomsView;
import com.fs.uixmpp.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class RoomsTest extends TestBase {

	private String roomName = "room1";

	private int timeoutMillis = 1000 * 1000;

	private String manager = BossModelI.M_CENTER;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		// TOTO this may be late for not listen to the event.

	}

	public void testRoomManagerControl() {

		this.finishing.add("binding");
		this.finishing.add("room.created");
		this.finishing.add("youjoin");
		this.finishing.add("he.join");
		this.finishing.add("he.exit");//

		this.dump();

		// login
		SessionModelI sm = this.rootModel.find(SessionModelI.class, true);
		sm.setAccount("acc-001");
		sm.setValue(SessionModelI.L_XMPP_USER, "acc-001");
		sm.setValue(SessionModelI.L_XMPP_PASSWORD, "acc-001");
		
		// TODO auto testing by env.
		sm.setValue(SessionModelI.L_DOMAIN, "thinkpad");// TODO how to support
														// test by
		// environment?
		this.xclient.addHandler(ResourceBindSuccessEvent.TYPE,
				new HandlerI<ResourceBindSuccessEvent>() {

					@Override
					public void handle(ResourceBindSuccessEvent e) {
						RoomsTest.this.onResourceBindSuccessEvent(e);
					}
				});

		this.delayTestFinish(timeoutMillis);

		sm.setAuthed(true);

	}

	protected void onResourceBindSuccessEvent(ResourceBindSuccessEvent e) {

		// how to monitor the event when session established?

		RoomsView rmv = this.getRoomsView();

		StringEditorI rnameE = rmv.find(StringEditorI.class,
				RoomsView.TESTING_ROOM_NAME_EDITOR, true);// TODO

		rnameE.input(StringData.valueOf(this.roomName));// Join room name

		this.tryFinish("binding");// band

		RoomsModelI rmm = this.rootModel.find(RoomsModelI.class, true);
		rmm.addHandler(ModelChildEvent.TYPE, new HandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				RoomsTest.this.onRoomsChildEvent(e);
			}
		});
		// set the manager to the center of frwk

		rmm.setManagerForNewRoom(manager);//

		//
		rmv.clickAction(RoomsModelI.A_JOIN);
	}

	/**
	 * Oct 21, 2012
	 */
	protected void onRoomsChildEvent(ModelChildEvent e) {
		ModelI m = e.getChild();
		if (!(m instanceof RoomModelI)) {
			return;
		}

		this.tryFinish("room.created");//

		RoomModelI rm = (RoomModelI) m;
		rm.addValueHandler(RoomModelI.L_JOINED,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						RoomsTest.this.onRoomJoin(e);
					}
				});
		rm.addHandler(ModelChildEvent.TYPE, new HandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				RoomsTest.this.onRoomChildEvent(e);
			}
		});
	}

	protected void onRoomChildEvent(ModelChildEvent e) {
		ModelI m = e.getChild();
		if (!(m instanceof OccupantModel)) {
			return;
		}
		OccupantModel om = (OccupantModel) m;
		// the below handler will miss the first setting if the event is
		// deffered.
		// TODO
		om.addValueHandler(OccupantModel.L_ISEXIT,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						RoomsTest.this.onOccupantExitEvent(e);
					}
				});
	}

	/**
	 * Oct 21, 2012
	 */
	protected void onOccupantExitEvent(ModelValueEvent e) {

		boolean exit = e.getValue(Boolean.FALSE);
		OccupantModel om = (OccupantModel) e.getModel();
		if (exit) {
			this.sendMessage("bye (jid:" + om.getJid() + ", " + om.getNick()
					+ ",role:" + om.getRole() + ")");
			this.tryFinish("he.exit");
		} else {
			this.sendMessage("welcome (jid:" + om.getJid() + ", "
					+ om.getNick() + ",role:" + om.getRole() + ")");
			this.tryFinish("he.join");
		}

	}

	/**
	 * Oct 21, 2012
	 */
	protected void onRoomJoin(ModelValueEvent e) {
		this.tryFinish("youjoin");

	}

	protected RoomsView getRoomsView() {

		RoomsView rmv = this.client.getRoot().find(RoomsView.class, true);
		return rmv;
	}

	protected void sendMessage(String msg) {

		RoomView rv = this.client.getRoot().find(RoomView.class, true);
		String manager = rv.getManager();
		assertEquals("manager not correct", this.manager, manager);

		System.out.println("room view's parent:" + rv.getParent());

		StringEditorI me = rv.find(StringEditorI.class, true);//
		me.input(StringData.valueOf(msg));
		//
		rv.clickAction(RoomModelI.A_SEND);//
	}

}
