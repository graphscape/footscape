/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.event;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class GChatDisconnectedEvent extends GChatConnectEvent {

	public static final Event.Type<GChatDisconnectedEvent> TYPE = new Event.Type<GChatDisconnectedEvent>(
			GChatConnectEvent.TYPE, "unkown");

	/**
	 * @param type
	 */
	public GChatDisconnectedEvent(GChatControlI gc) {
		super(TYPE, gc, false);
	}

}
