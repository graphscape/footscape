/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpConnectCreatedNotifyMH extends NotifyMH {

	/**
	 * @param c
	 */
	public ExpConnectCreatedNotifyMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		//TODO expId
		this.getControl(MainControlI.class, true).refreshExpConnect(null);

	}

}
