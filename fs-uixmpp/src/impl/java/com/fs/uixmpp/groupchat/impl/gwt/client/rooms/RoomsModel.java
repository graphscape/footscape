/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.rooms;

import java.util.List;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.Jid.Bare;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;
import com.fs.uixmpp.groupchat.impl.gwt.client.room.RoomModel;

/**
 * @author wu
 * 
 */
public class RoomsModel extends ModelSupport implements RoomsModelI {

	// public static final Location L_NICK = Location.valueOf("_nick");

	// public static final Location L_DOMAIN = Location.valueOf("_domain");

	private XmppModelI xmpp;

	/**
	 * @param name
	 */
	public RoomsModel(String name, XmppModelI xmpp) {
		super(name);
		this.xmpp = xmpp;
		ControlUtil.addAction(this, RoomsModelI.A_JOIN);
	}

	public String getRoomNameEditing(boolean force) {
		return (String) this.getValue(L_ROOMNAME_EDITING);
	}

	@Override
	public List<RoomModelI> getRoomModelList() {
		return this.getChildList(RoomModelI.class);
	}

	@Override
	public RoomModelI getRoomModelByJid(Jid.Bare jid, boolean force) {
		return this.getChild(RoomModelI.class, jid.toString(), force);
	}

	@Override
	public RoomModel addRoomModel(Bare rbareId, String nick) {
		String mname = rbareId.toString();
		RoomModel rt = new RoomModel(mname, this.xmpp, rbareId, nick);//
		String manager = this.getValue(String.class, L_MANAGER_FOR_NEW_ROOM);
		rt.setManager(manager);//
		rt.parent(this);

		return rt;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setManagerForNewRoom(String manager) {
		this.setValue(L_MANAGER_FOR_NEW_ROOM, manager);
	}

	@Override
	public String getNick(boolean force) {
		String rt = this.getValue(String.class, L_NICK);
		if (force && rt == null) {
			throw new UiException("no nick name for:" + this);
		}
		return rt;

	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public RoomModelI getRoomModelByRoomName(String rname, boolean force) {
		List<RoomModelI> rtL = this.getRoomModelList();
		RoomModelI rt = null;
		for (RoomModelI rm : rtL) {
			if (rm.getRoomJid().isUser(rname)) {
				if (rt != null) {
					throw new UiException("duplicated room name:" + rname
							+ " in rooms model:" + this);
				}
				rt = rm;
			}
		}
		if (rt == null && force) {
			throw new UiException("no room found:" + rname + " in rooms model:"
					+ this);
		}
		return rt;

	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public String getDomain() {
		//
		return this.getValue(String.class, L_DOMAIN);

	}
}
