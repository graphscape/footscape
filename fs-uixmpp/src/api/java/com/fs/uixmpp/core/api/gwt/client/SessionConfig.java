/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 22, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicore.api.gwt.client.support.MapProperties;

/**
 * @author wu
 * 
 */
public class SessionConfig extends MapProperties<String> {

	public static final String K_DOMAIN = ":DOMAIN";
	public static final String K_RESOURCE = ":RESOURCE";
	public static final String K_URL = ":URL";
	public static final String K_USER = ":USER";
	public static final String K_PASS = ":PASS";

	public String getDomain() {
		return this.getProperty(K_DOMAIN, true);

	}

	public String getResource() {
		return (String) this.getProperty(K_RESOURCE, true);

	}

	public String getUrl() {
		return (String) this.getProperty(K_URL, true);
	}

	public String getUser() {
		return this.getProperty(K_USER);
	}

	public String getBareJidAsString() {
		return this.getBareJid().toString();
	}

	public Jid getBareJid() {
		String user = this.getUser();
		String domain = this.getDomain();
		String jidS = user + "@" + domain;
		Jid rt = Jid.valueOf(jidS);
		return rt;
	}

	public Jid getFullJid() {
		Jid bjid = this.getBareJid();

		return Jid.valueOf(bjid.getBare(), this.getResource());
	}

	public String getPassword() {
		String rt = (String) this.getProperty(K_PASS, true);
		return rt;
	}
}
