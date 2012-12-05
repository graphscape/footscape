/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.event;

import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppEvent;

/**
 * @author wu UnknownEvent is the event that no converter to process it,then the
 *         only way to go the convert them into unknown event.
 */
public class UnknownEvent extends XmppEvent {

	public static final Type<UnknownEvent> TYPE = new Type<UnknownEvent>();

	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param type
	 */
	public UnknownEvent(String name, XmppControlI src) {
		super(TYPE, src);
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.Event#toString()
	 */
	@Override
	public String toString() {
		return "UnknownEvent:" + name + ":no converter from this event";
	}

}
