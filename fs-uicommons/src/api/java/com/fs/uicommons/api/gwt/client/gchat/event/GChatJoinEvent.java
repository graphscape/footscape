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
public class GChatJoinEvent extends GChatEvent {

	public static final Event.Type<GChatJoinEvent> TYPE = new Event.Type<GChatJoinEvent>(GChatEvent.TYPE);

	protected String participantId;
	
	/**
	 * @param type
	 * @param gc
	 * @param gid
	 */
	public GChatJoinEvent(GChatControlI gc, String gid, String pid) {
		super(TYPE, gc, gid);
		this.participantId = pid;
	}

	/**
	 * @return the participantId
	 */
	public String getParticipantId() {
		return participantId;
	}

}
