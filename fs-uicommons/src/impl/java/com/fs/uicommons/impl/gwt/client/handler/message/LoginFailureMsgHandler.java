/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.ErrorCodes;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
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
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class LoginFailureMsgHandler extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public LoginFailureMsgHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		MessageData req = t.getMessage();

		AccountsLDW accs = AccountsLDW.getInstance();
		ErrorInfosData eis = (ErrorInfosData) req.getPayload(UiResponse.ERROR_INFO_S);
		if (eis.containsErrorCode(ErrorCodes.AUTH_FAILURE)) {//

			// the saved account/email/password not valid for some reason
			// 1)password is changed by some other means.
			// 2)annonymous account is removed by serve side for some reason.
			// then clean the saved info, and re run the procedure.

			Boolean isSaved = req.getBoolean("isSaved", true);
			String type = req.getString("type", true);

			if (isSaved) {// only process auto auth
				if (type.equals("registered")) {//
					RegisteredAccountLDW acc1 = accs.getRegistered();
					acc1.invalid();// try using the anonymous login.
					//try auto auth with anonymous
					AutoLoginHandler.autoLogin(this.getEndpoint(), t.getSource());//try again,with anonymous
				} else if (type.equals("anonymous")) {
					AnonymousAccountLDW acc2 = accs.getAnonymous();
					acc2.invalid();// clean and try again: create a new
									// anonymous and login
					AutoLoginHandler.autoLogin(this.getEndpoint(), t.getSource());//try agin, signup anonymous
					// again

				} else {
					throw new UiException("bug,no this type:" + type);
				}

			} else {// not saved info,but user provided info,so notify user this
					// error.
				LoginViewI lv = this.getRootView().find(LoginViewI.class, true);
				lv.addErrorInfo(eis);
			}

		}
	}
}
