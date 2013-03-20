/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.message;

import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.AnonymousAccountLDW;
import com.fs.uicommons.impl.gwt.client.handler.action.AutoLoginHandler;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class SignupAnonymousMsgHandler extends UiHandlerSupport implements
		MessageHandlerI<EndpointMessageEvent> {

	/*
	 * Jan 2, 2013
	 */
	/**
	 * @param c
	 */
	public SignupAnonymousMsgHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(EndpointMessageEvent t) {
		//
		UiClientI client = t.getSource().getClient(true);
		ControlManagerI cm = client.getChild(ControlManagerI.class, true);
		MessageData res = t.getMessage();//

		AccountsLDW sai = AccountsLDW.getInstance();
		// save to client and schedule to send again the auth action,see
		// processRequest of this.
		String accId = (String) res.getPayload("accountId", true);//
		String password = (String) res.getPayload("password", true);
		// accountId and password generated by serverside,so saving it
		// before auth is ok.
		AnonymousAccountLDW aal = sai.getAnonymous();
		aal.save(accId, password);// save anonymous
									// account.

		AutoLoginHandler.autoLogin(this.getEndpoint(), t.getSource());

	}

}
