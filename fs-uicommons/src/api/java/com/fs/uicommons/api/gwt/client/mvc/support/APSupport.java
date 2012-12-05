/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wu
 * 
 */
public class APSupport implements ActionProcessorI {

	public APSupport() {
	}

	/*
	 */
	@Override
	public void processRequest(final ControlI c, final String a, UiRequest req) {

	}

	@Override
	public void processResponse(final ControlI c, final String a, UiResponse res) {
		if (res.getErrorInfos().hasError()) {
			this.processResponseWithError(c, a, res, res.getErrorInfos());
		} else {
			this.processResponseSuccess(c, a, res);
		}
	}

	protected void processResponseSuccess(final ControlI c, final String a,
			UiResponse res) {

	}

	protected void processResponseWithError(final ControlI c, final String a,
			UiResponse res, ErrorInfosData eis) {

	}

}
