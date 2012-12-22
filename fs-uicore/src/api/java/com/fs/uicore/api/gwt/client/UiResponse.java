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

	protected ErrorInfosData errorInfoData;

	/**
	 * @param fc
	 */
	public UiResponse(UiRequest req) {
		this(null, req);
	}

	public UiResponse(String name, UiRequest req) {
		super(name);
		this.errorInfoData = new ErrorInfosData();
		this.request = req;

	}

	public void onResponse(ObjectPropertiesData pls, ErrorInfosData eis) {
		this.setPayloads(pls);
		if (eis != null) {
			this.errorInfoData.addAll(eis);
		}
	}

	public ErrorInfosData getErrorInfos() {
		return this.errorInfoData;
	}

	/**
	 * @return the request
	 */
	public UiRequest getRequest() {
		return request;
	}
}
