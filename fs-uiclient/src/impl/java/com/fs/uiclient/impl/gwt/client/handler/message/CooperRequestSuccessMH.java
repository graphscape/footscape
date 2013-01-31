/**
 *  Jan 5, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wuzhen
 * 
 */
public class CooperRequestSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public CooperRequestSuccessMH(ContainerI c) {
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
		//DO nothing,wait the incoming cr notifiy
	}

}
