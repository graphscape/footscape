/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpEditSubmitMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpEditSubmitMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		String expId = res.getString("expId", true);

		UserExpListControlI c = this.getControl( UserExpListControlI.class, true);

		c.refresh(expId);

	}
}
