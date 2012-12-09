package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.ErrorCodes;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class SubmitAP extends APSupport {

	@Override
	public void processRequest(final ControlI c, final String a, UiRequest req) {

		LoginModelI lm = c.getModel();

		// this submit
		if (lm.getIsUsingSavedAccount()) {// check saved
			AccountsLDW accs = AccountsLDW.getInstance();
			RegisteredAccountLDW acc1 = accs.getRegistered();
			AnonymousAccountLDW acc2 = accs.getAnonymous();
			if (acc1.isValid()) {
				req.setPayload("isSaved", BooleanData.valueOf(true));
				req.setPayload("type", StringData.valueOf("registered"));
				req.setPayload("email", StringData.valueOf(acc1.getEmail()));
				req.setPayload("password",
						StringData.valueOf(acc1.getPassword()));
			} else if (acc2.isValid()) {
				req.setPayload("isSaved", BooleanData.valueOf(true));
				req.setPayload("type", StringData.valueOf("anonymous"));

				String accId = acc2.getAccountId();

				String password = acc2.getPassword();
				req.setPayload("accountId", StringData.valueOf(accId));
				req.setPayload("password", StringData.valueOf(password));

			} else {// has not saved account,create it first and then call this
					// submit again.

				ControlUtil.triggerAction(lm, LoginModelI.A_ANONYMOUS);
				// break the current request
				req.setIsLocal(true);// NOTE break this request,

			}
		} else {// else,user login by view input
			req.setPayload("isSaved", BooleanData.valueOf(false));
			req.setPayload("type", StringData.valueOf("registered"));

			String email = lm.getEmail();

			String password = lm.getPassword();
			req.setPayload("email", StringData.valueOf(email));
			req.setPayload("password", StringData.valueOf(password));

		}

	}

	@Override
	public void processResponseSuccess(ControlI c, String a, UiResponse res) {
		if (res.getRequest().isLocal()) {// break
			return;//
		}
		LoginModelI lm = c.getModel();

		boolean isSaved = res.getRequest().getPayLoadAsBoolean("isSaved",
				Boolean.FALSE);//
		if (isSaved) {// successed for saved,do nothing,remain the same

			lm.setIsUsingSavedAccout(false);// allow user to input with view

		} else {// user provide info
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
		this.doLoginSuccess(lm, res);

	}

	private void doLoginSuccess(LoginModelI lm, UiResponse res) {

		SessionModelI sm = lm.getSessionModel();

		ObjectPropertiesData opd = res.getPayloads();

		BooleanData isAnony = (BooleanData) opd.getProperty("isAnonymous");

		sm.setIsAnonymous(isAnony.getValue());
		StringData sid = (StringData) opd.getProperty("loginId");
		StringData accId = (StringData) opd.getProperty("accountId");
		sm.setAccount(accId.getValue());
		sm.setLoginId(sid.getValue());// session id.

		if (isAnony.getValue()) {
			loginAnonymousSuccess(sm, opd);
		} else {
			loginNormalSuccess(sm, opd);//
		}
		sm.setLoginRequired(false);//
		sm.setAuthed(true);//
	}

	protected void loginAnonymousSuccess(SessionModelI sm,
			ObjectPropertiesData opd) {

	}

	protected void loginNormalSuccess(SessionModelI sm, ObjectPropertiesData opd) {

		StringData domain = (StringData) opd.getProperty("xmpp.domain");
		StringData xuser = (StringData) opd.getProperty("xmpp.user");
		StringData xpassword = (StringData) opd.getProperty("xmpp.password");
		sm.setValue(SessionModelI.L_DOMAIN, domain.getValue());
		sm.setXmppUser(xuser.getValue());
		sm.setXmppPassword(xpassword.getValue());

	}

	@Override
	protected void processResponseWithError(final ControlI c, final String a,
			UiResponse res, ErrorInfosData eis) {
		UiRequest req = res.getRequest();
		AccountsLDW accs = AccountsLDW.getInstance();

		if (eis.containsErrorCode(ErrorCodes.FAILED_LOGIN_NOTFOUND_ACCOUNT_OR_PASSWORD)) {//

			// the saved account/email/password not valid for some reason
			// 1)password is changed by some other means.
			// 2)annonymous account is removed by serve side for some reason.
			// then clean the saved info, and re run the procedure.

			LoginModelI lm = (LoginModelI) c.getModel();
			Boolean isSaved = req.getPayLoadAsBoolean("isSaved", true);
			String type = req.getPayLoadAsString("type", true);
			
			if (isSaved) {// only process auto auth
				if (type.equals("registered")) {//
					RegisteredAccountLDW acc1 = accs.getRegistered();
					acc1.invalid();// try using the anonymous login.
					ControlUtil.triggerAction(lm, LoginModelI.A_SUBMIT);// try
					// again

				} else if (type.equals("anonymous")) {
					AnonymousAccountLDW acc2 = accs.getAnonymous();
					acc2.invalid();// clean and try again: create a new
									// anonymous and login
					ControlUtil.triggerAction(lm, LoginModelI.A_SUBMIT);// try
					// again

				} else {
					throw new UiException("bug,no this type:" + type);
				}

			}

		}
	}
}
