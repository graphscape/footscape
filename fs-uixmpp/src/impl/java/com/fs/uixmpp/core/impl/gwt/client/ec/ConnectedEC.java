/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.Connector;
import tigase.jaxmpp.core.client.Connector.ConnectorEvent;

import com.fs.uixmpp.core.api.gwt.client.event.ConnectedEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class ConnectedEC extends EventConverter<ConnectorEvent, ConnectedEvent> {

	/**
	 * @param et
	 */
	public ConnectedEC() {
		super(Connector.Connected, "Connected");
	}

	@Override
	public ConnectedEvent convert(Context<ConnectorEvent> e) {

		ConnectedEvent rt = new ConnectedEvent(e.getXmpp());
		return rt;
	}

}
