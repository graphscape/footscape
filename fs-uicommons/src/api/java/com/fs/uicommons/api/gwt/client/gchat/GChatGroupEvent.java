/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class GChatGroupEvent extends GChatEvent {

	public static final Event.Type<GChatGroupEvent> TYPE = new Event.Type<GChatGroupEvent>(GChatEvent.TYPE, "unkown");

	protected String groupId;

	/**
	 * @param type
	 */
	public GChatGroupEvent(Type<? extends GChatGroupEvent> type, GChatControlI gc, String gid) {
		super(type, gc);
		this.groupId = gid;
	}

	public GChatControlI getGChat() {
		return this.getSource(GChatControlI.class);
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

}
