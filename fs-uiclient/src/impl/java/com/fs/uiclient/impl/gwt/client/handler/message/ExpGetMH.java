/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.coper.MyExp;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
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

		ObjectPropertiesData expD = (ObjectPropertiesData) res.getPayloads().getProperty("expectation", true);
		// refresh the title of the tab
		MainControlI c = this.getControl(MainControlI.class, true);
		MyExp exp = new MyExp(expD);
		
		c.setExpDetail(exp);
		//
		
	}

}
