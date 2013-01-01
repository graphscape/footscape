/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wu
 * 
 */
public class AfterClientStartEvent extends ClientEvent {
	public static Type<AfterClientStartEvent> TYPE = new Type<AfterClientStartEvent>(ClientEvent.TYPE);

	/** */
	public AfterClientStartEvent(UiClientI client) {
		super(TYPE, client);
	}

}