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
public abstract class GChatParticipantEvent extends GChatGroupEvent {

	public static final Event.Type<GChatParticipantEvent> TYPE = new Event.Type<GChatParticipantEvent>(
			GChatGroupEvent.TYPE, "participant");

	protected String participantId;

	/**
	 * @param type
	 * @param gc
	 * @param gid
	 */
	public GChatParticipantEvent(
			Event.Type<? extends GChatParticipantEvent> type, GChatControlI gc,
			String gid, String pid) {
		super(type, gc, gid);
		this.participantId = pid;
	}

	/**
	 * @return the participantId
	 */
	public String getParticipantId() {
		return participantId;
	}

}
