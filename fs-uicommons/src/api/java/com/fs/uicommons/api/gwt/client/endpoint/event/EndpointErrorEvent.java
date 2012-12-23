/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.data.ErrorInfoData;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public class EndpointErrorEvent extends EndpointEvent {

	public static final Type<EndpointErrorEvent> TYPE = new Type<EndpointErrorEvent>(EndpointEvent.TYPE);

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
