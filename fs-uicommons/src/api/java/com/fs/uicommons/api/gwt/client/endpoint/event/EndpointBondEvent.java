/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class EndpointBondEvent extends EndpointEvent {

	public static final Type<EndpointBondEvent> TYPE = new Type<EndpointBondEvent>(EndpointEvent.TYPE);

	protected String sessionId;

	/**
	 * @param type
	 */
	public EndpointBondEvent(EndPointI c, String sessionId) {
		super(TYPE, c);
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

}
