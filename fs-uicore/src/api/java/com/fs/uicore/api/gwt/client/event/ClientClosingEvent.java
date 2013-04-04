/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wu
 * 
 */
public class ClientClosingEvent extends StateChangeEvent {

	public static final Type<ClientClosingEvent> TYPE = new Type<ClientClosingEvent>("client-closing");

	/**
	 * @param type
	 */
	public ClientClosingEvent(UiClientI client) {
		super(TYPE, client);
	}

}
