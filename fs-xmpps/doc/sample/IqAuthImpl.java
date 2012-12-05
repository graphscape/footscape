/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.xmpps.impl.stanza;

import tigase.jaxmpp.core.client.xml.Element;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqAuthI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu<br>
 *         http://xmpp.org/extensions/xep-0078.html
 */
public class IqAuthImpl extends IqImpl implements IqAuthI {

	public IqAuthImpl(XmppSession xci) {
		super(xci);

		ElementI qr = this.getQuery();
		qr.setAttribute("xmlns", "jabber:iq:auth");

	}

	/**
	 * @param ele
	 * @param ci
	 */
	public IqAuthImpl(Element ele, XmppSession ci) {
		super(ele, ci);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.xmppclient.api.stanza.UserRegisterI#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String uname) {
		this.getQuery().setChildText("username", uname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.xmppclient.api.stanza.UserRegisterI#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {

		this.getQuery().setChildText("password", password);
	}

}
