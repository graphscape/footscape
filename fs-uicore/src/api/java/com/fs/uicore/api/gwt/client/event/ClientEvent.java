/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class ClientEvent extends Event {
	public static Type<ClientEvent> TYPE = new Type<ClientEvent>("client");

	/** */
	public ClientEvent(Type<? extends ClientEvent> type, UiClientI client) {
		super(type, client);
	}

}