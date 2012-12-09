/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wuzhen
 * 
 */
public class UiResponse extends UiTransfer {

	public static final String ERROR_INFO_S = "_ERROR_INFO_S";// NOTE must same
																// as
																// ResponseImpl
																// in server
																// side.

	protected UiRequest request;

	/**
	 * @param fc
	 */
	public UiResponse(UiRequest req) {
		this(null, req);
	}

	public UiResponse(String name, UiRequest req) {
		super(name);
		this.request = req;
		//
		this.getPayloads().setProperty(ERROR_INFO_S, new ErrorInfosData());
		//
	}

	public ErrorInfosData getErrorInfos() {
		return (ErrorInfosData) this.getPayloads().getProperty(ERROR_INFO_S);
	}

	/**
	 * @return the request
	 */
	public UiRequest getRequest() {
		return request;
	}
}
