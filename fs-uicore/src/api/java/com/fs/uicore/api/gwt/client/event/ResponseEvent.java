/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class ResponseEvent extends Event {

	public static final Event.Type<ResponseEvent> TYPE = new Event.Type<ResponseEvent>();

	protected UiResponse response;

	/**
	 * @param type
	 */
	public ResponseEvent(Event.Type<? extends ResponseEvent> type,
			UiClientI src, UiResponse res) {
		super(type, src);
		this.response = res;
	}

	public UiResponse getResponse() {
		return response;
	}

}
