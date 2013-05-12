/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wu
 * 
 * 
 */
public class ClientStartFailureEvent extends ClientEvent {
	public static Type<ClientStartFailureEvent> TYPE = new Type<ClientStartFailureEvent>(
			ClientEvent.TYPE, "start-failure");

	/** */
	public ClientStartFailureEvent(UiClientI client) {
		super(TYPE, client);
	}

}