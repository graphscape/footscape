/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class EndpointEvent extends Event {

	public static final Type<EndpointEvent> TYPE = new Type<EndpointEvent>();

	/**
	 * @param type
	 */
	public EndpointEvent(Type<? extends EndpointEvent> type, EndPointI source) {
		super(type, source);

	}

	public EndPointI getChannel() {
		return (EndPointI) this.source;
	}

}
