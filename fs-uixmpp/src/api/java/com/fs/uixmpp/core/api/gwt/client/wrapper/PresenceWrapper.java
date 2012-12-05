/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 25, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.wrapper;

import com.fs.uixmpp.core.api.gwt.client.StanzaI;

/**
 * @author wu
 * 
 */
public class PresenceWrapper extends StanzaWrapper {

	/**
	 * @param st
	 */
	public PresenceWrapper(StanzaI st) {
		super(st);

	}

	public static PresenceWrapper valueOf(StanzaI st) {
		return PresenceWrapper.valueOf(new StanzaWrapper(st));
	}

	public static PresenceWrapper valueOf(StanzaWrapper sw) {
		if (!sw.isName(StanzaI.N_PRESENCE)) {
			return null;
		}
		return new PresenceWrapper(sw.stanza);
	}

}
