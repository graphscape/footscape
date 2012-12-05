/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 22, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class XmppEvent extends Event {

	public XmppEvent(Type type, XmppControlI src) {
		super(type, src);
	}

	public XmppControlI getXmppControl() {
		return (XmppControlI) this.source;
	}
}
