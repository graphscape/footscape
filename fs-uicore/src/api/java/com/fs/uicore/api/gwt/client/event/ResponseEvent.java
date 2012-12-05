/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ResponseEvent extends Event {

	public static final Event.Type<ResponseEvent> TYPE = new Event.Type<ResponseEvent>();

	/**
	 * @param type
	 */
	public ResponseEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
