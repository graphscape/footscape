/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.impl;

import java.util.UUID;
import java.util.concurrent.Future;

import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.XMLException;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.Jid;
import com.fs.xmpps.api.Jid.Bare;
import com.fs.xmpps.api.StanzaI;

/**
 * @author wu
 * 
 */
public class StanzaImpl extends ElementImpl implements StanzaI {

	protected XmppSession connection;

	public StanzaImpl(String name, XmppSession ci) {
		super(name);
		this.connection = ci;
		String id = UUID.randomUUID().toString();
		this.setAttribute("id", id);
	}

	public StanzaImpl(Element ele, XmppSession ci) {

		super(ele);
		this.connection = ci;
	}

	public Type getType() {
		String name = this.getAttribute("type");
		return Type.valueOf(name);
	}

	@Override
	public void setType(Type type) {
		this.setAttribute("type", type.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.StanzaI#send()
	 */
	@Override
	public Future<StanzaI> send() {

		return this.connection.sendAsync(this);

	}

	@Override
	public StanzaI sendSync() {
		return this.connection.sendSync(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.StanzaI#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.getAttribute("id");
	}

	@Override
	public String toString() {
		try {
			String rt = this.element.getAsString();
			return rt;
		} catch (XMLException e) {
			throw new FsException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.StanzaI#getErrorInfos()
	 */
	@Override
	public ErrorInfos getErrorInfos() {
		ErrorInfos rt = new ErrorInfos();
		Type type = this.getType();
		if ("error".equals(type.getName())) {
			ElementI err = this.getChild("error", true);
			String ecode = err.getAttribute("code");
			String msg = err.toString();
			rt.add(new ErrorInfo(ecode, msg));
		}
		return rt;
	}

	@Override
	public void assertNoError() {
		ErrorInfos er = this.getErrorInfos();

		if (er.hasError()) {
			throw new FsException(er.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.StanzaI#setTo(java.lang.String)
	 */
	@Override
	public void setTo(Jid to) {
		this.setAttribute("to", to.toString());
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public Bare getToBare() {
		//
		return this.getTo().getBare();
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public Bare getFromBare() {
		//
		return this.getFrom().getBare();
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public Jid getTo() {
		//
		String name = this.getAttribute("to");
		return Jid.valueOf(name);
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public Jid getFrom() {

		String name = this.getAttribute("from");
		return Jid.valueOf(name);
	}

	/*
	 * Oct 26, 2012
	 */
	@Override
	public void setFrom(Jid jid) {
		this.setAttribute("from", jid.toString());
	}
}
