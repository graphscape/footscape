/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 7, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu Maintain the user session and plugin list.
 */
public interface XmppControlI extends ControlI {

	public StanzaI createStanza(String name);

	public StanzaI createStanza(String name, StanzaI.Type type);

	public void active();
}
