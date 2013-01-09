/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class CooperConfirmSuccessMH extends MHSupport {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		// remove cr from model

		CooperControlI cc = this.getControl(t, CooperControlI.class, true);
		String crId = t.getMessage().getString("crId", true);
		cc.removeIncomingCr(crId);
		// uelist
		UserExpListControlI uc = this.getControl(t, UserExpListControlI.class,
				true);

		uc.incomingCrConfirmed(crId);
	}

}
