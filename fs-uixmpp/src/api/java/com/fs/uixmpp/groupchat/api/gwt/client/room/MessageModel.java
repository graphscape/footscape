/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.room;

import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;

/**
 * @author wu
 * 
 */
public class MessageModel extends ModelSupport {
	
	public static final Location L_FROM = Location.valueOf("_from");
	
	public static final Location L_TO = Location.valueOf("_to");

	private XmppModelI xmpp;
	/**
	 * @param name
	 */
	public MessageModel(String name, XmppModelI xmpp, Jid from, Jid to, String msg) {
		super(name);
		this.xmpp = xmpp;
		this.setValue(L_FROM, from);
		this.setValue(L_TO, to);
		this.setDefaultValue(msg);

	}

	public String getBody() {
		return (String) this.getDefaultValue();
	}
	
	public Jid getFrom() {
		return (Jid) this.getValue(L_FROM);

	}

	public Jid getTo() {
		return (Jid) this.getValue(L_TO);

	}
	
}
