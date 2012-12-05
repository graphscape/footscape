/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 12, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqDiscoI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu
 * 
 */
public class IqDiscoImpl extends IqImpl implements IqDiscoI {

	/**
	 * @param xci
	 */
	public IqDiscoImpl(XmppSession xci) {
		super(xci);
		ElementI qE = this.getOrAddChild("query");
		qE.setXmlns("http://jabber.org/protocol/disco#items");
	}

}
