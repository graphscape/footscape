/**
 * Jul 20, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class AfterClientStartEvent extends ClientEvent {
	public static Type<AfterClientStartEvent> TYPE = new Type<AfterClientStartEvent>(ClientEvent.TYPE,
			"started");

	/** */
	public AfterClientStartEvent(UiClientI client) {
		super(TYPE, client);
	}

	public UiClientI getClient() {
		return (UiClientI) this.source;
	}

	public EndPointI getEndPoint() {
		return this.getClient().getEndpoint(true);
	}

}