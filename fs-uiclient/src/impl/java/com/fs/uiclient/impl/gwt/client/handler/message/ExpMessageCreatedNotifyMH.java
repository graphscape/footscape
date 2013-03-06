/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpMessageCreatedNotifyMH extends NotifyMH {

	/**
	 * @param c
	 */
	public ExpMessageCreatedNotifyMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		String accId = this.getEndpoint().getUserInfo().getAccountId();

		MsgWrapper req = this.newRequest(Path.valueOf("/expm/search"));
		req.setPayload("accountId2", accId);//

		this.sendMessage(req);

	}

}
