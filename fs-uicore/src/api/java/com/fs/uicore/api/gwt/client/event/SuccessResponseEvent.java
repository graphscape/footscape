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
public class SuccessResponseEvent extends ResponseEvent {

	public static final Event.Type<SuccessResponseEvent> TYPE = new Event.Type<SuccessResponseEvent>(
			ResponseEvent.TYPE);

	/**
	 * @param src
	 */
	public SuccessResponseEvent(UiClientI src, UiResponse res) {
		super(TYPE, src, res);
	}

}
