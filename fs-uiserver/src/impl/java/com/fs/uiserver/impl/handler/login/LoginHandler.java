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
			vl = this.createValidator("auth");//see handleAuth method's signature.
			
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
		
		String accountId = (String) req.getPayload("accountId");
		if (accountId == null) {
			String email = (String) req.getPayload("email");
			if (email == null) {
				res.getErrorInfos()
						.add(new ErrorInfo(
								"invalid request,no accountId or email provided."));
				return;
			}
			AccountInfo ai = this.dataService.getNewest(AccountInfo.class,
					AccountInfo.EMAIL, email, false);
			if (ai == null) {
				res.getErrorInfos().add(
						new ErrorInfo("failed.login", "no this email"));// TODO
																		// no
				return;
			}
			accountId = ai.getAccountId();

		}

		Account acc = this.dataService.getNewestById(Account.class, accountId,
				false);

		if (acc == null) {// no this account or password

			res.getErrorInfos().add(
					new ErrorInfo("failed.login",
							"no this account or password error"));// TODO no
			// this
			// account
			// of
			// password
			// error.
		} else {

			AccountInfo xai = this.dataService.getNewest(AccountInfo.class,
					AccountInfo.PK_ACCOUNT_ID, acc.getId(), false);
			if(xai ==null){//anonymous user has no account info?.
				
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

	// client try to find the user info in client side for login;
	// if not found ,it will request a anonymous account by this method;
	// request a account for anonymous user.
	@Handle("auth")
	//
	public void handleAuth(HandleContextI hc, RequestI req, ResponseI res,
			ValidateResult<RequestI> vr) {
		//
		//
		//
		String accId = (String) req.getPayload("accountId");
		if (accId == null) {// create a anonymous account.
			String id = UUID.randomUUID().toString();
			Account an = new Account().forCreate(this.dataService);

			an.setId(id);//
			an.setIsAnonymous(true);
			an.setPassword(id);//
			an.setNick(id);
			an.save(true);

			res.setPayload("accountId", an.getId());
			res.setPayload("password", an.getPassword());
			
		} else {// todo go though the super handler method.
			// allow forward.
			this.handleSubmit(hc, req, res, vr);//
		}

	}
}
