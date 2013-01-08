/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wu
 * 
 */
public class BeforeClientStartEvent extends ClientEvent {
	public static Type<BeforeClientStartEvent> TYPE = new Type<BeforeClientStartEvent>(ClientEvent.TYPE, "before-start");

	/** */
	public BeforeClientStartEvent(UiClientI client) {
		super(TYPE, client);
	}

}