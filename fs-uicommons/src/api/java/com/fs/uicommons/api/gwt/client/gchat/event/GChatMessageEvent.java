/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.event;

import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatGroupEvent;
import com.fs.uicommons.api.gwt.client.gchat.wrapper.MessageMW;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class GChatMessageEvent extends GChatGroupEvent {

	public static final Event.Type<GChatMessageEvent> TYPE = new Event.Type<GChatMessageEvent>(
			GChatGroupEvent.TYPE);

	protected MessageMW message;

	/**
	 * @param type
	 * @param gc
	 * @param gid
	 */
	public GChatMessageEvent(GChatControlI gc, String gid, MessageMW md) {
		super(TYPE, gc, gid);
		this.message = md;

	}

	public MessageMW getMessage() {
		return message;
	}

}
