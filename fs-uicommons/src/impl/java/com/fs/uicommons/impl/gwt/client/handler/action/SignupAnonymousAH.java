package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class SignupAnonymousAH extends ActionHandlerSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		MsgWrapper req = this.newRequest(Path.valueOf("/signup/anonymous"));

		this.sendMessage(ae, req);
	}

}
