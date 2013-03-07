/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class CooperConfirmSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public CooperConfirmSuccessMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		// remove cr from model
		String crId = t.getMessage().getString("crId", true);
		UserExpListControlI uc = this.getControl(UserExpListControlI.class, true);

	}

}
