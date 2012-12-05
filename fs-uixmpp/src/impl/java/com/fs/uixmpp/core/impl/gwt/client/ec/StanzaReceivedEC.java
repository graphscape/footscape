/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.Connector;
import tigase.jaxmpp.core.client.Connector.ConnectorEvent;
import tigase.jaxmpp.core.client.xml.Element;

import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.event.StanzaReceivedEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;
import com.fs.uixmpp.core.impl.gwt.client.StanzaImpl;

/**
 * @author wu
 * 
 */
public class StanzaReceivedEC extends
		EventConverter<ConnectorEvent, StanzaReceivedEvent> {

	/**
	 * @param et
	 */
	public StanzaReceivedEC() {
		super(Connector.StanzaReceived, "StanzaReceived");
	}

	@Override
	public StanzaReceivedEvent convert(Context<ConnectorEvent> e) {

		Element ele = e.getEvent().getStanza();

		StanzaI st = new StanzaImpl(ele, e.getXmpp());
		StanzaReceivedEvent rt = new StanzaReceivedEvent(e.getXmpp(), st);
		return rt;
	}

}
