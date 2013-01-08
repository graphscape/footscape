/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.event;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatGroupEvent;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class GChatGroupCreatedEvent extends GChatGroupEvent {

	public static final Event.Type<GChatGroupCreatedEvent> TYPE = new Event.Type<GChatGroupCreatedEvent>(
			GChatGroupEvent.TYPE, "unkown");

	/**
	 * @param type
	 * @param gc
	 * @param gid
	 */
	public GChatGroupCreatedEvent(GChatControlI gc, String gid) {
		super(TYPE, gc, gid);
	}

}
