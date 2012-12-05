/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.builder;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.StanzaBuilder;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu <br>
 *         todo remove this http://xmpp.org/extensions/xep-0045.html 7.2.1
 *         Groupchat 1.0 Protocol
 * 
 *         In order to participate in the discussions held in a multi-user chat
 *         room, a user MUST first become an occupant by entering the room. In
 *         the old groupchat 1.0 protocol, this was done by sending presence
 *         with no 'type' attribute to <room@service/nick>, where "room" is the
 *         room ID, "service" is the hostname of the chat service, and "nick" is
 *         the user's desired nickname within the room:
 */
public class MucPresenceBuilder extends StanzaBuilder {

	/**
	 * @param stanza
	 */
	public MucPresenceBuilder(XmppControlI xsi) {
		super("presence", null, xsi);// TODO type
		ElementI x = this.stanza.addChild("x");
		x.setXmlns("http://jabber.org/protocol/muc");
	}

	public void setPassword(String pw) {
		ElementI x = this.stanza.getChild("x", true);
		ElementI pe = x.addChild("password");
		pe.setText(pw);//
	}

}