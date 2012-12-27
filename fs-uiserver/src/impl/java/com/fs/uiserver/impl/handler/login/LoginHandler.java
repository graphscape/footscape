/**
 * Aug 1, 2012
 */
package com.fs.uiserver.impl.handler.login;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.uiserver.ErrorCodes;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu<br>
 *         Signon with username/password,got a session id.
 */
public class LoginHandler extends UiHandlerSupport {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoginHandler.class);

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
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
			return;
		}
		// login success for the account found.
		AccountInfo xai = this.dataService.getNewest(AccountInfo.class,
				AccountInfo.PK_ACCOUNT_ID, acc.getId(), false);
		if (xai == null) {// anonymous user has no account info?.

		}
		res.setPayload("isAnonymous", acc.getIsAnonymous());

		SessionGd li = this.loginSuccess(acc, hc);
		res.setPayload("sessionId", li.getId());//
		res.setPayload("accountId", acc.getId());// account?
		// decode.

	}

	// create login record
	protected SessionGd loginSuccess(Account acc, HandleContextI hc) {
		String cid = this.getClientId(hc);

		SessionGd rt = new SessionGd();
		rt.setProperty(SessionGd.CLIENTID, cid);
		rt.setProperty(SessionGd.ACCID, acc.getId());
		rt.setProperty("isAnonymous", acc.getIsAnonymous());//

		this.sessionManager.createSession(rt);
		String sid = rt.getId();
		this.clientManager.bindingSession(cid, sid);
		LOG.debug("binding,clientId:" + cid + ",sessionId:" + sid);

		this.save(acc.getIsAnonymous(), rt);
		return rt;
	}

	protected Session save(boolean ano, SessionGd sgd) {
		Session rt = new Session().forCreate(this.dataService);
		rt.setAccountId(sgd.getAccountId());
		rt.setId(sgd.getId());
		rt.setClientId(sgd.getClientId());//
		rt.setProperty(Session.PK_IS_ANONYMOUS, ano);
		rt.save(true);//
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
