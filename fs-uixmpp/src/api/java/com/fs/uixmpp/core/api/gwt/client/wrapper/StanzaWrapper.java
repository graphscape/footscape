/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 25, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.wrapper;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uixmpp.core.api.gwt.client.ElementWrapper;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu
 * 
 */
public class StanzaWrapper extends ElementWrapper {

	protected StanzaI stanza;

	protected boolean readOnly;

	public StanzaWrapper(StanzaI st) {
		super(st);
		this.stanza = st;
		this.readOnly = true;
	}

	protected StanzaWrapper(String name, XmppControlI xmpp) {
		super(xmpp.createStanza(name));
		this.stanza = (StanzaI) this.element;
		this.stanza.setXmlns("jabber:client");//
		this.readOnly = false;

	}

	public void send() {

		if (this.readOnly) {
			throw new UiException("readonly cannot be send out");
		}

		this.stanza.send();
	}

	public void setTo(Jid jid) {
		this.stanza.setTo(jid);
	}

	/**
	 * @return the stanza
	 */
	public StanzaI getStanza() {
		return stanza;
	}

	public void setType(StanzaI.Type type) {
		this.stanza.setType(type);
	}

	public boolean isType(StanzaI.Type type) {
		return type.equals(this.stanza.getType());
	}

	public boolean isBare(Jid.Bare bare) {
		return this.isFromBareJid(bare) || this.isToBare(bare);
	}

	public boolean isToBare(Jid.Bare bare) {

		return bare.equals(this.stanza.getToBare());
	}

	public boolean isFromBareJid(Jid.Bare bare) {

		return bare.equals(this.stanza.getFromBare());
	}
}
