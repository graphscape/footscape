/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class GetExpAP extends APSupport {

	/**
	 * Nov 28, 2012
	 * <p>
	 * Get the detail of the exp,user click in the list, to open the detail.
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		String expId = (String) c.getModel().getValue(
				UserExpListModelI.L_EXP_ID_GET_REQUIRED);
		req.setPayload("expId", StringData.valueOf(expId));//
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	protected void processResponseSuccess(ControlI c, String a, UiResponse res) {
		//
		//TODO
	}

}
