/**
 * Jul 17, 2012
 */
package com.fs.uicore.impl.gwt.client.filter;

import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.event.ErrorEvent;
import com.fs.uicore.api.gwt.client.support.UiFilterSupport;
import com.fs.uicore.impl.gwt.client.UiClientImpl;

/**
 * @author wu
 * 
 */
public class ErrorResponseFilter extends UiFilterSupport {

	public static final String ERROR_INFO_S = "_ERROR_INFO_S";// NOTE see ResponseImpl and EngineImpl

	protected UiClientImpl client;

	public ErrorResponseFilter(UiClientImpl c) {
		this.client = c;
	}

	/* */
	@Override
	protected void filterRequest(Context fc) {

	}

	/* */
	@Override
	protected void filterResponse(Context fc) {
		UiResponse res = fc.getResponse();
		ErrorInfosData error = (ErrorInfosData) res.getErrorInfos();
		if (error.hasError()) {
			new ErrorEvent(this.client, error.toString()).dispatch();
		}
	}

}
