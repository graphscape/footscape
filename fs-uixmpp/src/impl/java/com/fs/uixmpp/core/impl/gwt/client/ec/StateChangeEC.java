/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.Connector;
import tigase.jaxmpp.core.client.Connector.ConnectorEvent;

import com.fs.uixmpp.core.api.gwt.client.event.StateChangeEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class StateChangeEC extends
		EventConverter<ConnectorEvent, StateChangeEvent> {

	/**
	 * @param et
	 */
	public StateChangeEC() {
		super(Connector.StateChanged, "StateChanged");
	}

	@Override
	public StateChangeEvent convert(Context<ConnectorEvent> e) {

		StateChangeEvent rt = new StateChangeEvent(e.getXmpp());
		return rt;
	}

}
