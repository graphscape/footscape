/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 30, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class LocalEvent extends Event {

	public static final Event.Type<LocalEvent> TYPE = new Event.Type<LocalEvent>();

	/**
	 * @param type
	 */
	public LocalEvent(Type<? extends Event> type, UiObjectI src) {
		super(type, src);
		this.isGlobal = false;
	}

}
