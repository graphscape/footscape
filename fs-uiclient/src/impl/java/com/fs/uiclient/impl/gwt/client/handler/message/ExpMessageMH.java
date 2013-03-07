/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 */
public class ExpMessageMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpMessageMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		MessageData res = t.getMessage();
		
		List<ObjectPropertiesData> expL = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"expMessages", true);
		UserExpListControlI c = this.getControl(UserExpListControlI.class, true);
		for (int i = 0; i < expL.size(); i++) {
			ObjectPropertiesData msgD = expL.get(i);
			MessageData md = new MessageData("/exp/message");
			md.setPayloads(msgD);
			ExpMessage em = new ExpMessage(md);
			c.addOrUpdateExpMessage(em);
		}
		
	}

}
