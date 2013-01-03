/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.achat.AChatControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;

/**
 * @author wu
 * 
 */
public class AChatOpenAP extends ActionHandlerSupport {

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {

		AChatControlI ac = (AChatControlI) ae.getControl();
		ac.join();//

	}

}
