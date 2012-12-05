/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 14, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.builder;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * http://xmpp.org/extensions/xep-0045.html#roomconfig
 * <p>
 * 10.1.2 Creating an Instant Room
 * 
 * @author wu
 *         <p>
 *         <code>
 *         <iq to="room9@muc.fstest.com" xmlns="jabber:client" type="set" id="abb4a"> 
 *         		<query xmlns="http://jabber.org/protocol/muc#owner"> 
 *         			<x xmlns="jabber:x:data" type="submit"/> 
 *         		</query> 
 *         </iq>
 *         </code>
 */
public class MucIqOwnerBuilder extends IqBuilder {

	/**
	 * @param xci
	 */
	public MucIqOwnerBuilder(XmppControlI xci) {
		super(StanzaI.T_SET, xci);

		ElementI q = this.getQuery();
		q.setXmlns("http://jabber.org/protocol/muc#owner");
		ElementI x = q.addChild("x");
		x.setXmlns("jabber:x:data");
		x.setAttribute("type", "submit");
	}

}
