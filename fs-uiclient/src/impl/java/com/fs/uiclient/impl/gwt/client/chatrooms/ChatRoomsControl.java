/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.chatrooms;

import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomControlI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsControlI;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsModelI;
import com.fs.uiclient.impl.gwt.client.chatroom.ChatRoomControl;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;

/**
 * @author wu
 * 
 */
public class ChatRoomsControl extends ControlSupport implements
		ChatRoomsControlI {

	/**
	 * @param name
	 */
	public ChatRoomsControl(String name) {
		super(name);
		// this.addActionProcessor(ActivityModelI.A_REFRESH, new RefreshAP());
		this.localMap.put(ChatRoomsModelI.A_OPEN, true);

		this.addActionProcessor(ChatRoomsModelI.A_OPEN, new OpenAP());
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		//
		super.doModel(cm);
		RoomsModelI rsm = cm.getTopObject().find(RoomsModelI.class, true);
		// listen to the room created,it must be open from activity.
		// then here to attach the activity with the chat room.
		rsm.addHandler(ModelChildEvent.TYPE, new HandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				ChatRoomsControl.this.onChildRoomModel(e);
			}
		});
	}

	protected void onChildRoomModel(ModelChildEvent e) {
		// create chat room mvc
		if (!e.isAdd() || !(e.getChild() instanceof RoomModelI)) {
			return;// ignore removing event.
		}

		RoomModelI rm = (RoomModelI) e.getChild();

		String rshortname = rm.getRoomBareJid().getUser();// ar-actxxx;

		// TODO only allow activity room.
		String actId = this.getActivityIdFromRoomName(rshortname, true);

		// model
		ChatRoomsModelI csm = e.getModel().getTopObject()
				.find(ChatRoomsModelI.class, true);//
		ChatRoomModelI crm = csm.addChatRoom(rshortname, actId);

		// control

		ChatRoomControlI crc = new ChatRoomControl(rshortname);// Control's name
																// is related to
																// the activity
																// id.

		crc.model(crm);//

		this.getManager().addControl(crc);//

	}

	/**
	 * Oct 22, 2012
	 */
	public static String convertActivityId2RoomName(String actId) {
		//
		return ChatRoomsModelI.PREFIX_ACTIVITY + actId;
	}

	public static String getActivityIdFromRoomName(String rname, boolean force) {
		if (!rname.startsWith(ChatRoomsModelI.PREFIX_ACTIVITY)) {
			if (force) {
				throw new UiException("cannot convert the room name:" + rname
						+ " to activity id");
			}
			return null;
		}
		return rname.substring(ChatRoomsModelI.PREFIX_ACTIVITY.length());
	}

	/*
	 * Oct 25, 2012
	 */
	@Override
	public ChatRoomControlI getChatRoomControl(String actId, boolean force) {
		//
		String rname = convertActivityId2RoomName(actId);

		return this.getManager().getControl(ChatRoomControlI.class, rname,
				force);

	}

}
