/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 26, 2012
 */
package com.fs.xmpps.api.wrapper;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppSessionI;

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

	protected IqWrapper(XmppSessionI xmpp) {
		super("iq", xmpp);
	}

	public static IqWrapper create(XmppSessionI xmpp) {
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
