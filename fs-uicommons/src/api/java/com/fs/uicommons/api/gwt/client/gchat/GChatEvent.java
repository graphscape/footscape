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
public abstract class GChatEvent extends Event {

	public static final Event.Type<GChatEvent> TYPE = new Event.Type<GChatEvent>("gchat");

	/**
	 * @param type
	 */
	public GChatEvent(Type<? extends GChatEvent> type, GChatControlI gc) {
		super(type, gc);
	}

	public GChatControlI getGChat() {
		return this.getSource(GChatControlI.class);
	}

}
