/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 11, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.builder;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;

/**
 * @author wu<br>
 *         <iq type="set" id="bind_1"> <bind
 *         xmlns="urn:ietf:params:xml:ns:xmpp-bind"> <resource>Psi</resource>
 *         </bind> </iq>
 */
public class IqBindBuilder extends IqBuilder {

	/**
	 * @param xci
	 */
	public IqBindBuilder(StanzaI.Type type, XmppControlI xci) {
		super(type, xci);
		ElementI be = this.stanza.addChild("bind");
		be.setXmlns("urn:ietf:params:xml:ns:xmpp-bind");

	}

	public void setResource(String res) {
		ElementI be = this.stanza.getOrAddChild("bind");
		ElementI rE = be.getOrAddChild("resource");
		rE.setText(res);// TODO
	}

}
