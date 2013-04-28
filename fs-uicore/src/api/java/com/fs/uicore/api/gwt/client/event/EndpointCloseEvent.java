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

	public static final Type<EndpointCloseEvent> TYPE = new Type<EndpointCloseEvent>(EndpointEvent.TYPE,
			"close");

	protected String code;

	/**
	 * @param type
	 */
	public EndpointCloseEvent(EndPointI c, String code, String reason) {
		super(TYPE, c);
		this.code = code;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	protected String reason;
}
