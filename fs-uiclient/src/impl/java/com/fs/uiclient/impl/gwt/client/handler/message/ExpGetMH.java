/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.Constants;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpGetMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpGetMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		MessageData res = t.getMessage();

		ObjectPropertiesData exp = (ObjectPropertiesData) res.getPayloads().getProperty("expectation", true);
		// refresh the title of the tab
		MainControlI c = this.getControl(MainControlI.class, true);
		String expId = exp.getString(Constants.NK_ID, true);
		String body = exp.getString("body", true);
		c.updateTitleOfExpTab(expId, body);

	}

}
