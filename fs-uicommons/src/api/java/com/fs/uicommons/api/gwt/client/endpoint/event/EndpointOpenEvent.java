/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointOpenEvent extends EndpointEvent {

	public static final Type<EndpointOpenEvent> TYPE = new Type<EndpointOpenEvent>(
			EndpointEvent.TYPE);

	/**
	 * @param type
	 */
	public EndpointOpenEvent(EndPointI c) {
		super(TYPE, c);
	}

}
