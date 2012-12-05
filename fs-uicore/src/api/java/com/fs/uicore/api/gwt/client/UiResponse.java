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

	/**
	 * @param fc
	 */
	public UiResponse() {
		this(null);
	}

	public UiResponse(String name) {
		super(name);
		//
		this.getPayloads().setProperty(ERROR_INFO_S, new ErrorInfosData());
		//
	}

	public ErrorInfosData getErrorInfos() {
		return (ErrorInfosData) this.getPayloads().getProperty(ERROR_INFO_S);
	}

	public static UiResponse valueOf(UiHeaders header, ObjectPropertiesData pl) {
		UiResponse rt = new UiResponse("unknow");
		rt.getPayloads().setProperties(pl);
		return rt;
	}
}
