/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.event;

import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppEvent;

/**
 * @author wu
 * 
 */
public class DisconnectedEvent extends XmppEvent {

	public static final Type<DisconnectedEvent> TYPE = new Type<DisconnectedEvent>();

	/**
	 * @param type
	 */
	public DisconnectedEvent(XmppControlI src) {
		super(TYPE, src);
	}

}
