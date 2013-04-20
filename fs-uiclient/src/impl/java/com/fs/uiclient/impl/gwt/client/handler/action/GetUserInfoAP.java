/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;

/**
 * @author wu
 * 
 */
public class GetUserInfoAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public GetUserInfoAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		// find the coper model and perform action
		ControlManagerI mgr = ae.getSource().getClient(true).getChild(ControlManagerI.class, true);

		MainControlI mc = mgr.getControl(MainControlI.class, true);
		String accId = (String) ae.getProperty("accountId", true);
		mc.openUserInfo(true);

		MsgWrapper req = this.newRequest(UiClientConstants.RP_USERINFO_GET);
		
		req.getPayloads().setProperty("accountId", (accId));
		this.sendMessage(req);
	}

}
