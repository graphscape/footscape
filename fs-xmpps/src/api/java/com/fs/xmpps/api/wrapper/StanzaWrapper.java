/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 25, 2012
 */
package com.fs.xmpps.api.wrapper;

import java.util.concurrent.Future;

import com.fs.commons.api.lang.FsException;
import com.fs.xmpps.api.ElementWrapper;
import com.fs.xmpps.api.Jid;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppSessionI;

/**
 * @author wu
 * 
 */
public class StanzaWrapper extends ElementWrapper {

	protected StanzaI stanza;

	protected boolean readOnly;

	public StanzaWrapper(StanzaI st) {
		super(st);
		this.stanza = st;
		this.readOnly = true;
	}

	protected StanzaWrapper(String name, XmppSessionI xmpp) {
		super(xmpp.createStanza(name));
		this.stanza = (StanzaI) this.element;
		this.stanza.setXmlns("jabber:client");//
		this.readOnly = false;

	}

	public StanzaI sendSync() {
		StanzaI rt = this.stanza.sendSync();
		return rt;
	}

	public Future<StanzaI> send() {

		if (this.readOnly) {
			throw new FsException("readonly cannot be send out");
		}

		return this.stanza.send();

	}

	public void setTo(Jid jid) {
		this.stanza.setTo(jid);
	}

	/**
	 * @return the stanza
	 */
	public StanzaI getStanza() {
		return stanza;
	}

	public void setType(StanzaI.Type type) {
		this.stanza.setType(type);
	}

	public void setFrom(Jid jid) {
		this.stanza.setFrom(jid);
	}

	public boolean isType(StanzaI.Type type) {
		return type.equals(this.stanza.getType());
	}

	public boolean isBare(Jid.Bare bare) {
		return this.isFromBareJid(bare) || this.isToBare(bare);
	}

	public boolean isToBare(Jid.Bare bare) {

		return bare.equals(this.stanza.getToBare());
	}

	public boolean isFromBareJid(Jid.Bare bare) {

		return bare.equals(this.stanza.getFromBare());
	}
}
