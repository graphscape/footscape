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
public class GChatYouJoinEvent extends GChatGroupEvent {

	public static final Event.Type<GChatYouJoinEvent> TYPE = new Event.Type<GChatYouJoinEvent>(
			GChatGroupEvent.TYPE, "unkown");

	/**
	 * @param type
	 * @param gc
	 * @param gid
	 */
	public GChatYouJoinEvent(GChatControlI gc, String gid, String pid) {
		super(TYPE, gc, gid);
	}

}
