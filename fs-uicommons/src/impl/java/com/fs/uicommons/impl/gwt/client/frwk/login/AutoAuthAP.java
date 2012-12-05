/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 14, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 *         <p>
 *         Auto auth through data in client side.
 *         <p>
 *         this is called after got the session id.
 */
public class AutoAuthAP extends APSupport {

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		// TODO find data reserved in client side.
		// this account may be anonymous ore registed user.
		LoginModelI lm = (LoginModelI) c.getModel();
		String accountId = lm.getAccountIdSaved();
		String password = lm.getPasswordSaved();

		if (accountId != null) {
			req.setPayload("accountId", StringData.valueOf(accountId));
			req.setPayload("password", StringData.valueOf(password));
		}
		// else server side will create a anonymous user for this request.
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void processResponseSuccess(ControlI c, String a, UiResponse res) {

		StringData loginId = (StringData) res.getPayload("loginId", false);

		if (loginId == null) {// the first request, anonymous accountId must be
								// created

			// save to client and schedule to send again the auth action,see
			// processRequest of this.
			LoginModelI lm = (LoginModelI) c.getModel();
			StringData accId = (StringData) res.getPayload("accountId", true);//
			StringData password = (StringData) res.getPayload("password", true);
			lm.saveAccountAndPassword(accId.getValue(), password.getValue());//
			ControlUtil.triggerAction(c.getModel(), LoginModelI.A_AUTH);//
		} else {// the auth success .
			LoginLogic.loginSuccess((LoginModelI) c.getModel(), res);
		}
	}
}
