/**
 * Aug 1, 2012
 */
package com.fs.uiserver.impl.handler.login;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.NodeTypes;
import com.fs.dataservice.api.expapp.wrapper.Account;
import com.fs.dataservice.api.expapp.wrapper.AccountInfo;
import com.fs.dataservice.api.expapp.wrapper.Login;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.ErrorCodes;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu<br>
 *         Signon with username/password,got a session id.
 */
public class LoginHandler extends UiHandlerSupport {

	private String domain;

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.domain = cfg.getProperty("xmpp.domain", true);//
	}

	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		{
			ValidatorI<RequestI> vl = this.createValidator("submit");
			vl.addExpression("payloads.property['password']!=null");
			//

		}

	}

	/**
	 * <code>
	 * 	{
		  "bool" : {
		    "must" : [ {
		      "term" : {
		        "_type" : "account"
		      }
		    }, {
		      "term" : {
		        "email" : "user1@domain1.com"
		      }
		    }, {
		      "term" : {
		        "password" : "user1"
		      }
		    } ]
		  }
		}
	 * </code> Nov 4, 2012
	 */
	@Handle("submit")
	public void handleSubmit(HandleContextI hc, RequestI req, ResponseI res,
			ValidateResult<RequestI> vr) {
		if (res.getErrorInfos().hasError()) {
			return;//
		}
		String type = (String) req.getPayload("type");// anonymous/registered
		boolean isSaved = req.getPayload(Boolean.class, "isSaved",
				Boolean.FALSE);//

		String accountId;
		if (type.equals("registered")) {// registered

			String email = (String) req.getPayload("email", true);

			AccountInfo ai = this.dataService.getNewest(AccountInfo.class,
					AccountInfo.EMAIL, email, false);
			if (ai == null) {// not found account by email.
				res.getErrorInfos().addError(
						ErrorCodes.FAILED_LOGIN_NOTFOUND_ACCOUNT_OR_PASSWORD);// TODO
				// no
				return;
			}
			accountId = ai.getAccountId();

		} else {
			accountId = (String) req.getPayload("accountId", true);
		}

		Account acc = this.dataService.getNewestById(Account.class, accountId,
				false);

		if (acc == null) {// no this account or password

			res.getErrorInfos().addError(
					ErrorCodes.FAILED_LOGIN_NOTFOUND_ACCOUNT_OR_PASSWORD);// TODO
																			// no
			// this
			// account
			// of
			// password
			// error.
		} else {

			AccountInfo xai = this.dataService.getNewest(AccountInfo.class,
					AccountInfo.PK_ACCOUNT_ID, acc.getId(), false);
			if (xai == null) {// anonymous user has no account info?.

			}
			res.setPayload("isAnonymous", acc.getIsAnonymous());

			Login li = this.loginSuccess(acc, hc);
			res.setPayload("loginId", li.getUniqueId());//
			res.setPayload("accountId", acc.getId());// account?
			res.setPayload("xmpp.domain", this.domain);// for xmpp domain
			res.setPayload("xmpp.user", xai == null ? null : xai.getId());// TODO
																			// allow
																			// null
			// pass
			res.setPayload("xmpp.password",
					xai == null ? null : xai.getPassword());// TODO allow
			// null pass
			// encode
			// decode.

		}
	}

	// create login record
	protected Login loginSuccess(Account acc, HandleContextI hc) {

		Login rt = new Login().forCreate(this.dataService);
		rt.setSessionId(this.getSessionId(hc));
		rt.setAccountId(acc.getId());
		rt.setProperty(Login.PK_IS_ANONYMOUS, acc.getIsAnonymous());//
		rt.save(true);
		return rt;
	}

	@Handle("anonymous")
	// create anonymous account.
	public void handleAnonymous(HandleContextI hc, RequestI req, ResponseI res) {
		String id = UUID.randomUUID().toString();
		Account an = new Account().forCreate(this.dataService);

		an.setId(id);//
		an.setIsAnonymous(true);
		an.setPassword(id);//
		an.setNick(id);
		an.save(true);

		res.setPayload("accountId", an.getId());
		res.setPayload("password", an.getPassword());

	}
}
