/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class EndpointFreeEvent extends EndpointEvent {

	public static final Event.Type<EndpointFreeEvent> TYPE = new Event.Type<EndpointFreeEvent>(
			EndpointEvent.TYPE, "free");

	/**
	 * @param type
	 */
	public EndpointFreeEvent(EndPointI src) {
		super(TYPE, src);
	}

}
