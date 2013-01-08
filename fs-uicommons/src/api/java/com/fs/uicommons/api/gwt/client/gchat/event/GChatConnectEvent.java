/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.event;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatEvent;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class GChatConnectEvent extends GChatEvent {

	public static final Event.Type<GChatConnectEvent> TYPE = new Event.Type<GChatConnectEvent>(
			GChatEvent.TYPE, "unkown");

	protected boolean connected;

	/**
	 * @param type
	 */
	public GChatConnectEvent(Event.Type<? extends GChatConnectEvent> type,
			GChatControlI gc, boolean connected) {
		super(type, gc);
		this.connected = connected;
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}

}
