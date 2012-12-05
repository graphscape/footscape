/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.event;

import com.fs.uixmpp.core.api.gwt.client.StanzaEvent;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu
 * 
 */
public class StanzaReceivedEvent extends StanzaEvent {

	public static final Type<StanzaReceivedEvent> TYPE = new Type<StanzaReceivedEvent>();

	/**
	 * @param type
	 */
	public StanzaReceivedEvent(XmppControlI src, StanzaI stanza) {
		super(TYPE, src, stanza);
	}

}
