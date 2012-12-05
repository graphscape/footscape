/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.room;

import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.builder.MucPresenceBuilder;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;

/**
 * @author wu
 *
 */
public class JoinAP implements ActionProcessorI{

	/*
	 *Oct 21, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		// 
		RoomControlI rc = (RoomControlI)c;
		RoomModelI rm = (RoomModelI)c.getModel();
		Jid jid = rm.getRoomJid();
		// see MucModule.join
		XmppControlI xmpp = this.getXmpp(rc);
		MucPresenceBuilder pb = new MucPresenceBuilder(this.getXmpp(rc));
		pb.to(jid);// send presence to
		// pb.setPassword(pw);
		pb.send();
		// add listener to client
		

	}
	/*
	 *Oct 21, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		// 
		
	}
	public XmppControlI getXmpp(RoomControlI rc) {
		return rc.getClient(true).getChild(XmppControlI.class, true);
	}
	
}
