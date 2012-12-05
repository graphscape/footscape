/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 25, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class LoginLogic {

	public static void loginSuccess(LoginModelI lm, UiResponse res) {
		//
		SessionModelI sm = lm.getSessionModel();
		ObjectPropertiesData opd = res.getPayloads();
		BooleanData isAnony = (BooleanData) opd.getProperty("isAnonymous");

		sm.setIsAnonymous(isAnony.getValue());
		StringData sid = (StringData) opd.getProperty("loginId");
		StringData accId = (StringData) opd.getProperty("accountId");
		sm.setAccount(accId.getValue());
		sm.setLoginId(sid.getValue());// session id.

		if (isAnony.getValue()) {
			LoginLogic.loginAnonymousSuccess(sm, opd);
		} else {
			LoginLogic.loginNormalSuccess(sm, opd);//
		}
		sm.setLoginRequired(false);//
		sm.setAuthed(true);//
	}

	protected static void loginAnonymousSuccess(SessionModelI sm,
			ObjectPropertiesData opd) {

	}

	protected static void loginNormalSuccess(SessionModelI sm,
			ObjectPropertiesData opd) {

		StringData domain = (StringData) opd.getProperty("xmpp.domain");
		StringData xuser = (StringData) opd.getProperty("xmpp.user");
		StringData xpassword = (StringData) opd.getProperty("xmpp.password");
		sm.setValue(SessionModelI.L_DOMAIN, domain.getValue());
		sm.setXmppUser(xuser.getValue());
		sm.setXmppPassword(xpassword.getValue());

	}
}
