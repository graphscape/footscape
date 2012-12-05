/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.xmpps.api.wrapper;

import com.fs.xmpps.api.XmppSessionI;

/**
 * @author wu
 *         <p>
 *         2012-08-08 16:00:50 MessageRouter.processPacket() FINEST: Processing
 *         packet: from=c2s@thinkpad/127.0.0.1_5222_127.0.0.1_40501,
 *         to=sess-man@thinkpad, DATA= <iq id="Ltffg" xmlns="jabber:client"
 *         to="thinkpad" type="set"> <query xmlns="jabber:iq:register">
 *         <name>User2
 *         </name><username>user2</username><password>user2</password>
 *         <email>user2@thinkpad</email></query> </iq>
 */
public class RegisterWrapper extends IqWrapper {

	/**
	 * @param xmpp
	 */
	protected RegisterWrapper(XmppSessionI xmpp) {
		super(xmpp);
		this.query().getQuery(true).setXmlns("jabber:iq:register");

	}

	public static RegisterWrapper valueOf(XmppSessionI xs) {
		return new RegisterWrapper(xs);
	}

	public void setUsername(String uname) {
		this.getQuery(true).setChildText("username", uname);
	}

	public void setPassword(String password) {

		this.getQuery(true).setChildText("password", password);
	}

}
