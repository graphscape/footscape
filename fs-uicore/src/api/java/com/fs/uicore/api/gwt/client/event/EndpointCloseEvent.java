/**
 *  Dec 21, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.endpoint.EndPointI;


/**
 * @author wuzhen
 * 
 */
public class EndpointCloseEvent extends EndpointEvent {

	public static final Type<EndpointCloseEvent> TYPE = new Type<EndpointCloseEvent>(
			EndpointEvent.TYPE, "close");

	/**
	 * @param type
	 */
	public EndpointCloseEvent(EndPointI c) {
		super(TYPE, c);
	}

}
