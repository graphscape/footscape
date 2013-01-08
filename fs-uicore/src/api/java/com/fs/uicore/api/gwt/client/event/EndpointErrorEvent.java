/**
 *  Dec 21, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointErrorEvent extends EndpointEvent {

	public static final Type<EndpointErrorEvent> TYPE = new Type<EndpointErrorEvent>(EndpointEvent.TYPE, "error");

	protected ErrorInfosData errors = new ErrorInfosData();

	/**
	 * @param type
	 */
	public EndpointErrorEvent(EndPointI c, String message) {
		super(TYPE, c);
		this.errors.add(new ErrorInfoData("unknow", message));
	}

	/**
	 * @return the errors
	 */
	public ErrorInfosData getErrors() {
		return errors;
	}

}
