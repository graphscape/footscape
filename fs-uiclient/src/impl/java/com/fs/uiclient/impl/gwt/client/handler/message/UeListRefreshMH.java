/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.NodeFields;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class UeListRefreshMH extends MHSupport {

	/**
	 * @param c
	 */
	public UeListRefreshMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		List<ObjectPropertiesData> ld = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"userExpList");
		UserExpListControlI uec = this.getControl(UserExpListControlI.class, true);
		
		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			String expId = (String) oi.getProperty(NodeFields.PK_ID, true);
			String body = (String) oi.getProperty(ExpEditView.F_BODY, true);

			DateData dd = (DateData) oi.getProperty(NodeFields.PK_TIMESTAMP, true);

			UserExpModel uem = new UserExpModel(expId);

			uem.setBody(body);//
			uem.setTimestamp(dd);
			uec.addOrUpdateUserExp(uem);
		}
	}

}
