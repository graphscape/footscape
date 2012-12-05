/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 26, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.wrapper;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu
 * 
 */
public class IqWrapper extends StanzaWrapper {

	/**
	 * @param st
	 */
	public IqWrapper(StanzaI st) {
		super(st);
	}

	protected IqWrapper(XmppControlI xmpp) {
		super("iq", xmpp);
	}

	public static IqWrapper create(XmppControlI xmpp) {
		IqWrapper rt = new IqWrapper(xmpp);
		return rt;
	}
	
	public IqWrapper query() {
		this.stanza.getOrAddChild("query");
		return this;
	}

	public ElementI getQuery(boolean force) {
		return this.stanza.getChild("query", force);
	}

}
