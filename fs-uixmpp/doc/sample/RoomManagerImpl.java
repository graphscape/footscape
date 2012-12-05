
/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 9, 2012
 */
package com.fs.uixmpp.muc.impl.gwt.client;

import java.util.List;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppI;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;
import com.fs.uixmpp.core.api.gwt.client.support.ManagerSupport;
import com.fs.uixmpp.muc.api.gwt.client.RoomI;
import com.fs.uixmpp.muc.api.gwt.client.RoomManagerI;

/**
 * @author wu see tigase.jaxmpp.core.client.xmpp.modules.MucModule see
 *         JaxmppCore.modulesInit();
 */
public class RoomManagerImpl extends ManagerSupport implements RoomManagerI {


	@Override
	protected void doAttach() {
		super.doAttach();
		XmppI xc = this.getXmppClient();
		
		// XmppSessionImpl xsi = (XmppSessionImpl)xs;//TODO abstract
		// EventConverterManagerI ecm = xsi.getECManager();//
		// ecm.addEventConverter(new YouJoinEC());//TODO add event processor not
		// only converter.

		// xs.addListener(ec, l);
		xc.addHandler(StanzaReceivedEvent.TYPE,
				new HandlerI<StanzaReceivedEvent>() {

					@Override
					public void handle(StanzaReceivedEvent e) {
						RoomManagerImpl.this.onEvent(e);
					}
				});
	}

	private void onEvent(StanzaReceivedEvent sre) {
		StanzaI st = sre.getStanza();
		if (!this.isInteresting(st)) {
			return;
		}

	}

	// see Jaxmpp's MucModule
	// this logic is just a translation from that place
	private boolean isInteresting(StanzaI st) {
		StanzaI.Type type = st.getType();

		if (!StanzaI.N_MESSAGE.equals(type) && !StanzaI.N_PRESENCE.equals(type)) {
			// is not message is not presence
			return false;// ignore
		}
		Jid fjid = st.getFrom();
		if (fjid == null) {// no from
			return false;
		}
		// is group chat,true
		if (StanzaI.T_GROUPCHAT.equals(st.getType())) {
			return true;
		}
		// if from one of room
		if (this.containsRoom(fjid.getBare())) {// resource or bare
			return true;
		} else {
			return false;
		}

	}

	/**
	 * TODO add Describe for UiObject.find(Describe des);
	 */
	@Override
	public RoomI getRoom(Jid.Bare jid, boolean force) {
		List<RoomI> rl = this.getChildList(RoomI.class);
		for (RoomI r : rl) {
			if (r.getJid().getBare().equals(jid)) {
				return r;
			}
		}

		if (force) {
			throw new UiException("no room found:" + jid);
		}
		return null;
	}

	@Override
	public RoomI getOrCreateRoom(Jid.Bare bare) {
		RoomI rt = this.getRoom(bare, false);

		if (rt == null) {
			// create one
			String nick = this.getXmppClient().getNickName();
			rt = new RoomImpl(Jid.valueOf(bare, nick));//
			rt.parent(this);//
		}
		return rt;
	}

	public boolean containsRoom(Jid.Bare jid) {
		RoomI rm = this.getRoom(jid, false);

		return rm != null;
	}

}