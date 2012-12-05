/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.xmpps.impl.stanza;

import tigase.jaxmpp.core.client.xml.Element;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqI;
import com.fs.xmpps.impl.StanzaImpl;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu
 * 
 */
public class IqImpl extends StanzaImpl implements IqI {

	public IqImpl(XmppSession xci) {
		super("iq", xci);
		this.setXmlns("jabber:client");//
		this.addChild("query");
	}

	/**
	 * @param ele
	 * @param ci
	 */
	public IqImpl(Element ele, XmppSession ci) {
		super(ele, ci);

	}

	@Override
	public void setFrom(String from) {
		this.setAttribute("from", from);
	}

	@Override
	public ElementI getQuery() {
		return this.getChild("query", true);

	}

	/*
	 *Oct 26, 2012
	 */
	@Override
	public void setType(String type) {
		// 
		
	}

}
