/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.xmpps.impl.stanza;

import tigase.jaxmpp.core.client.xml.Element;

import com.fs.commons.api.lang.Todo;
import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqRegisterI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu 2012-08-08 16:00:50 MessageRouter.processPacket() FINEST:
 *         Processing packet: from=c2s@thinkpad/127.0.0.1_5222_127.0.0.1_40501,
 *         to=sess-man@thinkpad, DATA= <iq id="Ltffg" xmlns="jabber:client"
 *         to="thinkpad" type="set"> <query xmlns="jabber:iq:register">
 *         <name>User2
 *         </name><username>user2</username><password>user2</password>
 *         <email>user2@thinkpad</email></query> </iq>
 */
public class IqRegisterImpl extends IqImpl implements IqRegisterI {

	public IqRegisterImpl(XmppSession xci) {
		super(xci);

		ElementI qr = this.getQuery();
		qr.setAttribute("xmlns", "jabber:iq:register");

	}

	/**
	 * @param ele
	 * @param ci
	 */
	public IqRegisterImpl(Element ele, XmppSession ci) {
		super(ele, ci);
	}

	public void setRemove(boolean remove) {
		ElementI q = this.getQuery();

		if (remove) {
			q.getOrAddChild("remove");
		} else {
			throw new Todo("todo");
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.xmppclient.api.stanza.UserRegisterI#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.getQuery().setChildText("email", email);

	}

}
