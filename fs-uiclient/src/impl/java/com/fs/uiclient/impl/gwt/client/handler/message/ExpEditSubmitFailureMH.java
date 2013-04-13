/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpEditSubmitFailureMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpEditSubmitFailureMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData req = t.getMessage();
		ErrorInfosData eis = (ErrorInfosData) req.getPayload(UiResponse.ERROR_INFO_S);
		MainControlI mc = this.getControl(MainControlI.class, true);
		ExpEditViewI sv = mc.openExpEditView();
		sv.addErrorInfo(eis);
	}

}
