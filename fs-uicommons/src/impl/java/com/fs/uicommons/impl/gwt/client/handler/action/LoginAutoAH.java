package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.AnonymousAccountLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class LoginAutoAH extends ActionHandlerSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		LoginModelI lm = ae.findModel(LoginModelI.class, true);//
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		AccountsLDW accs = AccountsLDW.getInstance();
		RegisteredAccountLDW acc1 = accs.getRegistered();
		AnonymousAccountLDW acc2 = accs.getAnonymous();
		if (acc1.isValid()) {
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("registered"));
			req.setProperty("email", (acc1.getEmail()));
			req.setProperty("password", (acc1.getPassword()));
		} else if (acc2.isValid()) {
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("anonymous"));

			String accId = acc2.getAccountId();

			String password = acc2.getPassword();
			req.setProperty("accountId", (accId));
			req.setProperty("password", (password));

		} else {// has not saved account,create it first and then call this
				// submit again.

			ControlUtil.triggerAction(lm, Actions.A_LOGIN_ANONYMOUS);
			// break the current request
			// req.setIsLocal(true);// NOTE break this request,
			return;
		}

		this.getEndpoint(ae).auth(req);
	}

}
