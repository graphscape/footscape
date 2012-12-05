/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.room;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.Role;

/**
 * @author wu
 * 
 */
public class OccupantModel extends ModelSupport {

	public static final Location L_ISEXIT = Location.valueOf("_isExit");

	public static final Location L_NICK = Location.valueOf("nick");
	public static final Location L_ROLE = Location.valueOf("role");
	public static final Location L_JID = ModelI.L_DEFAULT;

	/**
	 * @param name
	 */
	public OccupantModel(String name, Role role, Jid jid) {
		super(name);
		this.setValue(L_NICK, name);
		this.setValue(L_JID, jid);
		this.setValue(L_ROLE, role);
	}

	public Role getRole() {
		return (Role) this.getValue(L_ROLE);
	}

	public String getNick() {
		return this.getValue(String.class, L_NICK);
	}

	public Jid getJid() {
		return (Jid) this.getDefaultValue();
	}

	public void setJid(Jid jid) {
		this.setDefaultValue(jid);
	}

	/**
	 * @param b
	 */
	public void setExit(boolean b) {
		this.setValue(L_ISEXIT, Boolean.valueOf(b));
	}

}
