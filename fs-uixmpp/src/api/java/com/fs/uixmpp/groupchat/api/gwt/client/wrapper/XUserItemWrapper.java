/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 26, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.wrapper;

import com.fs.uixmpp.core.api.gwt.client.ElementI;
import com.fs.uixmpp.core.api.gwt.client.ElementWrapper;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.Role;

/**
 * @author wu
 * 
 */
public class XUserItemWrapper extends ElementWrapper {

	/**
	 * <item role="participant" jid="user2@thinkpad" affiliation="none"
	 * nick="user2thinkpad" />
	 * 
	 * @param ele
	 */
	public XUserItemWrapper(ElementI ele) {
		super(ele);
	}

	public Role getRole() {
		String name = this.element.getAttribute("role");
		return Role.valueOf(name);
	}

	public Jid getJid() {
		String name = this.element.getAttribute("jid");

		return Jid.valueOf(name);
	}

	public String getNick() {
		return this.element.getAttribute("nick");
	}

}
