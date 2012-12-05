package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class SubmitAP extends APSupport {

	@Override
	public void processResponseSuccess(ControlI c, String a, UiResponse res) {
		LoginLogic.loginSuccess((LoginModelI) c.getModel(), res);
	}

}
