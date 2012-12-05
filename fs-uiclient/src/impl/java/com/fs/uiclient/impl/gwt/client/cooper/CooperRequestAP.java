/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class CooperRequestAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		String id1 = (String) c.getModel().getValue(CooperModelI.L_EXPID1);
		String id2 = (String) c.getModel().getValue(CooperModelI.L_EXPID2);
		req.getPayloads().setProperty("expId1", StringData.valueOf(id1));
		req.getPayloads().setProperty("expId2", StringData.valueOf(id2));

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
