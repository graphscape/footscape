/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 11, 2012
 */
package com.fs.xmpps.impl.stanza;

import com.fs.xmpps.api.ElementI;
import com.fs.xmpps.api.session.stanza.IqBindI;
import com.fs.xmpps.impl.XmppSession;

/**
 * @author wu<br>
 *         <iq type="set" id="bind_1"> <bind
 *         xmlns="urn:ietf:params:xml:ns:xmpp-bind"> <resource>Psi</resource>
 *         </bind> </iq>
 */
public class IqBindImpl extends IqImpl implements IqBindI {

	/**
	 * @param xci
	 */
	public IqBindImpl(XmppSession xci) {
		super(xci);
		ElementI be = this.addChild("bind");
		be.setXmlns("urn:ietf:params:xml:ns:xmpp-bind");
		
	}

	/* (non-Javadoc)
	 * @see com.fs.xmppclient.api.stanza.IqBindI#setResource(java.lang.String)
	 */
	@Override
	public void setResource(String res) {
		ElementI be = this.getOrAddChild("bind");
		ElementI rE = be.getOrAddChild("resource");
		rE.setText(res);//TODO
	}

}
