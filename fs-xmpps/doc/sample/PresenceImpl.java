/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 14, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.PresenceI;
import com.fs.xmpps.impl.StanzaImpl;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu <presence to="room5@muc.fstest.com/user1" xmlns="jabber:client">
 *         <priority>5</priority> <c ext="cs ep-notify html"
 *         ver="caps-b75d8d2b25" xmlns="http://jabber.org/protocol/caps"
 *         node="http://psi-im.org/caps"/> <x
 *         xmlns="http://jabber.org/protocol/muc"/> </presence>
 */
public class PresenceImpl extends StanzaImpl implements PresenceI {

	/**
	 * @param name
	 * @param ci
	 */
	public PresenceImpl(XmppSession ci) {
		super("presence", ci);
		ElementI x = this.addChild("x");
		x.setXmlns("http://jabber.org/protocol/muc");
	}

}
