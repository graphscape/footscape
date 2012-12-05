/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import tigase.jaxmpp.core.client.UIDGenerator;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.XMLException;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.Jid.Bare;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;

/**
 * @author wu
 * 
 */
public class StanzaImpl extends ElementImpl implements StanzaI {

	protected XmppControl connection;

	public StanzaImpl(String name, XmppControl ci) {
		this(name, null, ci);
	}

	public StanzaImpl(String name, StanzaI.Type type, XmppControl ci) {
		super(name);

		this.connection = ci;
		String id = UIDGenerator.next();
		this.setAttribute("id", id);
		if (type != null) {
			this.setType(type);
		}
	}

	public StanzaImpl(Element ele, XmppControl ci) {
		super(ele);
		this.connection = ci;
	}

	public StanzaI.Type getType() {
		String types = this.getAttribute("type");
		StanzaI.Type rt = StanzaI.Type.valueOf(types);
		return rt;
	}

	@Override
	public void setType(StanzaI.Type type) {
		this.setAttribute("type", type.getName());

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
			throw new UiException("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.StanzaI#getErrorInfos()
	 */
	@Override
	public ErrorInfosData getErrorInfos() {
		ErrorInfosData rt = new ErrorInfosData();
		StanzaI.Type type = this.getType();
		if (StanzaI.T_ERROR.equals(type)) {
			ElementI err = this.getChild("error", true);
			String ecode = err.getAttribute("code");
			String msg = err.toString();
			rt.add(new ErrorInfoData(msg,ecode));
		}
		return rt;
	}

	@Override
	public void assertNoError() {
		ErrorInfosData er = this.getErrorInfos();

		if (er.hasError()) {
			throw new UiException(er.toString());
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

	@Override
	public Jid getTo() {
		String to = this.getAttribute("to");
		if (to == null) {
			return null;
		}
		return Jid.valueOf(to);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.api.gwt.client.StanzaI#send()
	 */
	@Override
	public void send() {
		this.connection.send(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.core.api.gwt.client.StanzaI#getFrom()
	 */
	@Override
	public Jid getFrom() {
		String rtS = this.getAttribute("from");
		return Jid.valueOf(rtS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.core.api.gwt.client.StanzaI#getFromBare()
	 */
	@Override
	public Bare getFromBare() {
		return Bare.valueOf(this.getFrom());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uixmpp.core.api.gwt.client.StanzaI#getToBare()
	 */
	@Override
	public Bare getToBare() {
		return Bare.valueOf(this.getTo());
	}
}