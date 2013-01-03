/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class EndpointUnbondEvent extends EndpointEvent {

	public static final Type<EndpointUnbondEvent> TYPE = new Type<EndpointUnbondEvent>(EndpointEvent.TYPE);

	/**
	 * @param type
	 */
	public EndpointUnbondEvent(EndPointI c) {
		super(TYPE, c);
	}

}
