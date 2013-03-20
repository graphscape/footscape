/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class LoginMsgHandler extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public LoginMsgHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		LoginViewI lm = this.getClient(true).find(LoginViewI.class, true);

		MessageData res = t.getMessage();
		boolean isSaved = res.getBoolean("isSaved", Boolean.FALSE);//
		if (isSaved) {// successed for saved,do nothing,remain the same

		} else {// user provide info from view.
				// String type = res.getRequest().getPayLoadAsString("type",
				// true);
				// type must be registered
			
			boolean saving = lm.isSavingAccount();
			AccountsLDW sai = AccountsLDW.getInstance();
			RegisteredAccountLDW acc = sai.getRegistered();

			if (saving) {
				String password = lm.getPassword();
				String email = lm.getEmail();
				acc.save(email, password);

			} else {
				acc.invalid();//
			}
		}
		this.doLoginSuccess(lm, t);

	}

	private void doLoginSuccess(LoginViewI lm, EndpointMessageEvent evt) {
		MessageData res = evt.getMessage();
		ObjectPropertiesData opd = res.getPayloads();

		Boolean isAnony = (Boolean) opd.getProperty("isAnonymous");
		String sidD = (String) opd.getProperty("sessionId");
		String accId = (String) opd.getProperty("accountId");

		new AfterAuthEvent(lm, sidD).dispatch();
	}
}
