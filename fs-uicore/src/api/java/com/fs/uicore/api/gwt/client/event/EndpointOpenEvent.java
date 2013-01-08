/**
 *  Dec 21, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.endpoint.EndPointI;


/**
 * @author wuzhen
 * 
 */
public class EndpointOpenEvent extends EndpointEvent {

	public static final Type<EndpointOpenEvent> TYPE = new Type<EndpointOpenEvent>(
			EndpointEvent.TYPE, "open");

	/**
	 * @param type
	 */
	public EndpointOpenEvent(EndPointI c) {
		super(TYPE, c);
	}

}
