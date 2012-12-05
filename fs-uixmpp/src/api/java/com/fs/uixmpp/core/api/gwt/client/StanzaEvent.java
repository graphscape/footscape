/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 22, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class StanzaEvent extends XmppEvent {

	private StanzaI stanza;

	public StanzaEvent(Type<? extends Event> type, XmppControlI src, StanzaI stanza) {
		super(type, src);
		this.stanza = stanza;
	}

	/**
	 * @return the stanza
	 */
	public StanzaI getStanza() {
		return stanza;
	}

}
