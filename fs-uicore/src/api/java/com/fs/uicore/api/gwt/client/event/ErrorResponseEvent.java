/**
 *  Dec 26, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class ErrorResponseEvent extends ResponseEvent {

	public static final Event.Type<ErrorResponseEvent> TYPE = new Event.Type<ErrorResponseEvent>(
			ResponseEvent.TYPE);

	/**
	 * @param src
	 */
	public ErrorResponseEvent(UiClientI src, UiResponse res) {
		super(TYPE, src, res);
	}

}
