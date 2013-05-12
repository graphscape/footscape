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
public class ClientConnectLostEvent extends ClientEvent {
	public static Type<ClientConnectLostEvent> TYPE = new Type<ClientConnectLostEvent>(ClientEvent.TYPE,
			"connection-lost");

	/** */
	public ClientConnectLostEvent(UiClientI client) {
		super(TYPE, client);
	}

}