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
 * @author wu http://xmpp.org/rfcs/rfc3921.html
 */
public class MessageBuilder extends StanzaBuilder {

	public MessageBuilder(XmppControlI xci) {
		this(null, xci);
	}

	public MessageBuilder(StanzaI.Type type, XmppControlI xci) {
		super("message", type, xci);
		this.stanza.setXmlns("jabber:client");//
	}

	public void setNick(String nick) {
		ElementI nE = this.stanza.addChild("nick");
		nE.setXmlns("http://jabber.org/protocol/nick");
		nE.setText(nick);
	}

	public MessageBuilder subject(String msg) {
		ElementI body = this.stanza.getOrAddChild("subject");
		body.setText(msg);//
		return this;
	}

	public MessageBuilder body(String msg) {
		ElementI body = this.stanza.getOrAddChild("body");
		body.setText(msg);//
		return this;
	}

	public ElementI getQuery() {
		return this.stanza.getChild("query", true);

	}

}