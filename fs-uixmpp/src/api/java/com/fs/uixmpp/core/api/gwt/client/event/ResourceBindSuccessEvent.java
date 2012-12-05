/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.api.gwt.client.event;

import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppEvent;

/**
 * @author wu AuthSuccess later is this.
 * 
 */
public class ResourceBindSuccessEvent extends XmppEvent {

	public static final Type<ResourceBindSuccessEvent> TYPE = new Type<ResourceBindSuccessEvent>();

	/**
	 * @param type
	 */
	public ResourceBindSuccessEvent(XmppControlI src) {
		super(TYPE, src);
	}

}
