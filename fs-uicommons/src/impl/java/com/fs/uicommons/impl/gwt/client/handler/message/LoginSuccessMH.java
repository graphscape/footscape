/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.ErrorCodes;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.AnonymousAccountLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicommons.impl.gwt.client.handler.action.AutoLoginHandler;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class LoginSuccessMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public LoginSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();

		AccountsLDW accs = AccountsLDW.getInstance();

		Boolean isSaved = res.getBoolean("isSaved", true);
		if (isSaved) {// // if by auto login,do nothing
			return;
		}
		String type = res.getString("type");

		// type must be registed.

		// is click by user.
		// not saved info,but user provided info,so notify user this
		// error.

		LoginViewI lv = this.getRootView().find(LoginViewI.class, true);
		if (!lv.isSavingAccount()) {
			return;
		}
		String email = res.getString("email", true);
		String password = res.getString("password", true);

		RegisteredAccountLDW acc1 = accs.getRegistered();
		acc1.save(email, password);
	}
}
