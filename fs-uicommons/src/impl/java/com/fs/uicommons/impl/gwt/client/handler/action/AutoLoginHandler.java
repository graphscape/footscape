package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.event.AutoLoginRequireEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.AnonymousAccountLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class AutoLoginHandler extends UiHandlerSupport implements EventHandlerI<AutoLoginRequireEvent> {

	/**
	 * @param c
	 */
	public AutoLoginHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(AutoLoginRequireEvent ae) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);

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
			new ActionEvent(ae.getSource(), Actions.A_LOGIN_ANONYMOUS).dispatch();
			return;
		}

		this.getEndpoint().auth(req);
	}

}
