/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.ErrorCodes;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.AnonymousAccountLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
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
public class LoginFailureMsgHandler implements MessageHandlerI<EndpointMessageEvent> {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		MessageData req = t.getMessage();

		AccountsLDW accs = AccountsLDW.getInstance();
		ErrorInfosData eis = (ErrorInfosData) req.getPayload(UiResponse.ERROR_INFO_S);
		if (eis.containsErrorCode(ErrorCodes.FAILED_LOGIN_NOTFOUND_ACCOUNT_OR_PASSWORD)) {//

			// the saved account/email/password not valid for some reason
			// 1)password is changed by some other means.
			// 2)annonymous account is removed by serve side for some reason.
			// then clean the saved info, and re run the procedure.

			LoginModelI lm = t.getSource().getClient(true).getRootModel().find(LoginModelI.class, true);
			Boolean isSaved = req.getBoolean("isSaved", true);
			String type = req.getString("type", true);

			if (isSaved) {// only process auto auth
				if (type.equals("registered")) {//
					RegisteredAccountLDW acc1 = accs.getRegistered();
					acc1.invalid();// try using the anonymous login.
					ControlUtil.triggerAction(lm, Actions.A_LOGIN_SUBMIT);// try
					// again

				} else if (type.equals("anonymous")) {
					AnonymousAccountLDW acc2 = accs.getAnonymous();
					acc2.invalid();// clean and try again: create a new
									// anonymous and login
					ControlUtil.triggerAction(lm, Actions.A_LOGIN_SUBMIT);// try
					// again

				} else {
					throw new UiException("bug,no this type:" + type);
				}

			}

		}
	}
}
