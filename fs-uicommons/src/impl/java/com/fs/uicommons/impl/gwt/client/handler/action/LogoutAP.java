/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wu
 * @deprecated
 */
public class LogoutAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LogoutAP(ContainerI c) {
		super(c);

	}

	/**
	 * @param c
	 */

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		UserInfo ui = this.getEndpoint().getUserInfo();
		if (!ui.isAnonymous()) {
			RegisteredAccountLDW ral = AccountsLDW.getInstance().getRegistered();
			if (ral.isValid()) {
				ral.invalid();
			}
		}

		this.getEndpoint().logout();//

	}

}
