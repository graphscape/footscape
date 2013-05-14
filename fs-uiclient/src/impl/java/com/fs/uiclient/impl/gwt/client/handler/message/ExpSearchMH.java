/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpSearchMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpSearchMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		ExpSearchViewI esv = this.getControl(MainControlI.class, true).openExpSearch(false);

		MessageData res = t.getMessage();
		MessageData req = res.getSource();
		int limit = (Integer) req.getPayload("maxResult", true);

		List<ObjectPropertiesData> expL = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"expectations", true);

		for (int i = 0; i < expL.size(); i++) {
			ObjectPropertiesData oi = expL.get(i);
			ExpItemModel ei = new ExpItemModel(oi);
			esv.addExpItem(ei);
		}
		if (expL.size() < limit) {
			esv.noMore();
		}

	}

}
