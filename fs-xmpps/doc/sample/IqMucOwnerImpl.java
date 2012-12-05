/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 14, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqMucOwnerI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu <iq to="room9@muc.fstest.com" xmlns="jabber:client" type="set"
 *         id="abb4a"> <query xmlns="http://jabber.org/protocol/muc#owner"> <x
 *         xmlns="jabber:x:data" type="submit"/> </query> </iq>
 */
public class IqMucOwnerImpl extends IqImpl implements IqMucOwnerI {

	/**
	 * @param xci
	 */
	public IqMucOwnerImpl(XmppSession xci) {
		super(xci);
		this.setType("set");
		ElementI q = this.getQuery();
		q.setXmlns("http://jabber.org/protocol/muc#owner");
		ElementI x = q.addChild("x");
		x.setXmlns("jabber:x:data");
		x.setAttribute("type", "submit");
	}

}
