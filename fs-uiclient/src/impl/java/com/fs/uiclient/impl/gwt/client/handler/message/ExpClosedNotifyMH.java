/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpClosedNotifyMH extends NotifyMH {

	/**
	 * @param c
	 */
	public ExpClosedNotifyMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		// TODO expId
		String expId = t.getMessage().getHeader("expId", true);
		// try close my exp
		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.expClosed(expId);
		//

	}

}
