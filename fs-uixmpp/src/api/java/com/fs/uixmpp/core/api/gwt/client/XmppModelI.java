/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 7, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu Maintain the user session and plugin list.
 */
public interface XmppModelI extends ModelI {

	// public static final String S_CONNECTED = "connected";

	public static final Location L_JID = Location.valueOf("jid");

	public static final Location L_CONNECTED = Location.valueOf("connected");

	public static final Location L_RESOURCE_BAND = Location.valueOf("resourceBand");

	public boolean isConnected();

	public void connected(boolean connected);
	
	public boolean isResourceBand();
	
	public void resourceBand(boolean band);

	public Jid getJid();
	
}
