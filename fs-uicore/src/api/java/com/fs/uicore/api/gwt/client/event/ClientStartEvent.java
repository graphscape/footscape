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
public class ClientStartEvent extends Event {
	public static Type<ClientStartEvent> TYPE = new Type<ClientStartEvent>();

	private String sessionId;
	
	/** */
	public ClientStartEvent(UiClientI client, String sessionId) {
		super(TYPE, client);
		this.sessionId = sessionId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	

}