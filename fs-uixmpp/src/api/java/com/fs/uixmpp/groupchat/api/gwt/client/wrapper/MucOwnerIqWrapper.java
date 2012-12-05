/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 26, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.wrapper;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.wrapper.IqWrapper;

/**
 * @author wu
 * 
 */
public class MucOwnerIqWrapper extends IqWrapper {

	/**
	 * @param xmpp
	 */
	protected MucOwnerIqWrapper(XmppControlI xmpp) {
		super(xmpp);
		// TODO Auto-generated constructor stub
	}

	public static MucOwnerIqWrapper create(XmppControlI xmpp, Jid.Bare to) {
		MucOwnerIqWrapper rt = new MucOwnerIqWrapper(xmpp);
		rt.setType(StanzaI.T_SET);
		rt.setTo(to.toJid());
		rt.query();
		ElementI qE = rt.getQuery(true);
		qE.setXmlns("http://jabber.org/protocol/muc#owner");
		ElementI xE = qE.addChild("x");
		xE.setXmlns("jabber:x:data");
		xE.setAttribute("type", "submit");
		return rt;
	}
}
