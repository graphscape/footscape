/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.chatroom;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatroom.PeerModel;
import com.fs.uiclient.impl.gwt.client.chatrooms.ChatRoomsControl;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;

/**
 * @author wu
 * 
 */
public class ChatRoomModel extends ModelSupport implements ChatRoomModelI {

	/**
	 * @param name
	 */
	public ChatRoomModel(String name, String actId) {
		super(name);
		this.setValue(L_ACTIVITY_ID, actId);
		String rname = ChatRoomsControl.convertActivityId2RoomName(actId);//
		this.setValue(L_ROOMNAME, rname);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public String getActivityId() {
		//
		return this.getValue(String.class, L_ACTIVITY_ID);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public String getRoomName() {
		//
		return this.getValue(String.class, L_ROOMNAME);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public boolean isActivityId(String aid) {
		//
		return this.getActivityId().equals(aid);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public ActivityModelI getActivityModel() {
		ActivitiesModelI asm = this.getTopObject().find(ActivitiesModelI.class,
				true);
		ActivityModelI rt = asm.getActivity(this.getActivityId());

		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public RoomModelI getRoomModel() {
		RoomsModelI rsm = this.getTopObject().find(RoomsModelI.class, true);
		String rname = this.getRoomName();//
		RoomModelI rt = rsm.getRoomModelByRoomName(rname, true);//
		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public PeerModel addPeer(String expId, String xmppUser) {
		//
		PeerModel rt = new PeerModel(expId, expId, xmppUser);
		rt.parent(this);//
		return rt;
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public PeerModel getPeerByXmppUser(String xmppUser, boolean force) {
		List<PeerModel> pL = this.getChildList(PeerModel.class);
		for (PeerModel p : pL) {
			if (p.isXmppUser(xmppUser)) {
				return p;
			}
		}
		if (force) {
			throw new UiException("no peer model found for xmppuser:"
					+ xmppUser + " in chat room:" + this);
		}
		return null;
	}

}
