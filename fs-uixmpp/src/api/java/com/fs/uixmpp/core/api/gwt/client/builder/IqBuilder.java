/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.builder;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.StanzaBuilder;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu
 * @deprecated use IqWrapper.create
 */
public class IqBuilder extends StanzaBuilder {

	public IqBuilder(StanzaI.Type type, XmppControlI xci) {
		super("iq", type, xci);
		this.stanza.setXmlns("jabber:client");//
		this.stanza.addChild("query");
	}

	public void setFrom(String from) {
		this.stanza.setAttribute("from", from);
	}

	public ElementI getQuery() {
		return this.stanza.getChild("query", true);

	}

}
