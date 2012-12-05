/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.JaxmppCore;
import tigase.jaxmpp.core.client.JaxmppCore.JaxmppEvent;

import com.fs.uixmpp.core.api.gwt.client.event.DisconnectedEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class DisconnectedEC extends
		EventConverter<JaxmppEvent, DisconnectedEvent> {

	/**
	 * @param et
	 */
	public DisconnectedEC() {
		super(JaxmppCore.Disconnected, "Disconnected");
	}

	@Override
	public DisconnectedEvent convert(Context<JaxmppEvent> e) {

		DisconnectedEvent rt = new DisconnectedEvent(e.getXmpp());
		return rt;
	}

}
