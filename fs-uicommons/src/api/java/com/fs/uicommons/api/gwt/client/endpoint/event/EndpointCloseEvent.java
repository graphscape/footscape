/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointCloseEvent extends EndpointEvent {

	public static final Type<EndpointCloseEvent> TYPE = new Type<EndpointCloseEvent>(
			EndpointEvent.TYPE);

	/**
	 * @param type
	 */
	public EndpointCloseEvent(EndPointI c) {
		super(TYPE, c);
	}

}
