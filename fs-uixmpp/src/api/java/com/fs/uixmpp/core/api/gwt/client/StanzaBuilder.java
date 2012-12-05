/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

/**
 * @author wu
 * 
 */
public class StanzaBuilder {
	protected StanzaI stanza;

	public StanzaBuilder(String name, XmppControlI xsi) {
		this(name, null, xsi);
	}

	public StanzaBuilder(String name, StanzaI.Type type, XmppControlI xsi) {
		this.stanza = xsi.createStanza(name, type);
	}

	public StanzaBuilder type(StanzaI.Type type) {
		this.stanza.setAttribute("type", type.getName());
		return this;
	}

	/**
	 * @param userRoomJid
	 */
	public StanzaBuilder to(Jid to) {
		this.stanza.setTo(to);

		return this;
	}

	public StanzaBuilder send() {
		this.stanza.send();
		return this;
	}

}
