/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.rooms;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;
import com.fs.uixmpp.groupchat.impl.gwt.client.room.RoomControl;

/**
 * @author wu
 * 
 */
public class RoomsControl extends ControlSupport implements RoomsControlI {

	public RoomsControl(String name) {
		super(name);
		this.localMap.put(RoomsModelI.A_JOIN, true);
		this.addActionProcessor(RoomsModelI.A_JOIN, new JoinAP());// TODO must
																	// by after
																	// authed
																	// and the
																	// L_NICK is
																	// addressed.
	}

	public RoomsModel getModel() {
		return (RoomsModel) this.model;
	}

	// see Jaxmpp's MucModule
	// this logic is just a translation from that place
	/**
	 * private boolean isInteresting(StanzaI st) { StanzaI.Type type =
	 * st.getType();
	 * 
	 * if (!StanzaI.N_MESSAGE.equals(type) && !StanzaI.N_PRESENCE.equals(type))
	 * { // is not message is not presence return false;// ignore } Jid fjid =
	 * st.getFrom(); if (fjid == null) {// no from return false; } // is group
	 * chat,true if (StanzaI.T_GROUPCHAT.equals(st.getType())) { return true; }
	 * // if from one of room // if (this.containsRoom(fjid.getBare())) {//
	 * resource or bare // return true; // } else { // return false; // } return
	 * false; }
	 */

	@Override
	public Jid.Bare getRoomBareJid(String rname) {
		String domain = this.getModel().getDomain();
		return Jid.valueOf(rname + "@" + domain).getBare();

	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		//
		super.doModel(cm);
		// listen to the authed event and calculate nick name
		SessionModelI sm = cm.getTopObject().find(SessionModelI.class, true);
		sm.addValueHandler(SessionModelI.L_IS_AUTHED,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						//
						RoomsControl.this.onAuthed(e);
					}
				});
	}

	public static String convertRoomName2RoomControlName(String rname) {
		return rname;
	}

	/**
	 * Oct 24, 2012
	 */
	protected void onAuthed(ModelValueEvent e) {
		SessionModelI sm = (SessionModelI) e.getModel();
		String nick = sm.getAccount();// TODO other way?
		String domain = sm.getDomain();
		this.getModel().setValue(RoomsModelI.L_DOMAIN, "muc." + domain);// NOTE.
		this.getModel().setValue(RoomsModelI.L_NICK, nick);

	}

	/*
	 * Oct 25, 2012
	 */
	@Override
	public RoomControlI getRoomControl(String rname, boolean force) {
		String cname = convertRoomName2RoomControlName(rname);
		return this.getManager().getControl(RoomControlI.class, cname, force);

	}

	@Override
	public void join() {
		String rname = this.getModel().getRoomNameEditing(false);
		if (rname == null) {
			// TODO alert error:please input name

			return;
		}
		this.join(rname);
	}

	/*
	 * Oct 25, 2012
	 */
	@Override
	public void join(String rname) {
		//

		Jid.Bare roomId = this.getRoomBareJid(rname);//
		RoomsModelI rmm = this.getModel();
		RoomModelI rm = rmm.getRoomModelByJid(roomId, false);

		if (rm == null) {
			String nick = rmm.getNick(true);
			rm = rmm.addRoomModel(roomId, nick);
			// no need to create view ,it is created by the manager's view.
			String cname = RoomsControl.convertRoomName2RoomControlName(rname);
			RoomControl rc = new RoomControl(cname);

			rc.setName(rname);// room name

			rc.model(rm);

			this.getManager().addControl(rc);//

			ControlUtil.triggerAction(rm, RoomModelI.A_JOIN);

		}
	}

}
