/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.achat;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class OpenAP implements ActionProcessorI {

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {

		AChatControlI ac = (AChatControlI) c;
		ac.join();//

	}

	/**
	 * Oct 22, 2012
	 */

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
