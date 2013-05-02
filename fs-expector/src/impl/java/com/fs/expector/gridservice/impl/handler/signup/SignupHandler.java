/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.handler.signup;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.expector.dataservice.api.NodeTypes;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.SignupRequest;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.ErrorCodes;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class SignupHandler extends ExpectorTMREHSupport {

	private static Logger LOG = LoggerFactory.getLogger(SignupHandler.class);

	// protected ConfirmCodeNotifierI confirmCodeNotifier;

	// protected boolean isNeedConfirm = false;

	public SignupHandler() {
		super();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		{
			ValidatorI<MessageI> vl = this.createValidator("submit");
			vl.addExpression(Constants.P_ERROR_SIGNUP_NICK, prefix + "['nick']!=null");
			vl.addExpression(Constants.P_ERROR_SIGNUP_EMAIL, prefix + "['email']!=null");
			vl.addExpression(Constants.P_ERROR_SIGNUP_PASSWORD, prefix + "['password']!=null");
			// passcode in session .
			// vl.addExpression("payloads.property['passcode']==property['session'].property['passcode']");
		}

		// this.confirmCodeNotifier =
		// this.top.finder(ConfirmCodeNotifierI.class).name("main").find(true);
	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	/* */
	@Override
	public void handle(MessageContext sc) {

		super.handle(sc);
	}

	@Handle("init")
	public void handleInit(MessageI req, ResponseI res, MessageContext hc) {

	}

	/**
	 * Submit the signup request,this is 1st step,next step is confirm.
	 * 
	 * @param req
	 * @param res
	 * @param hc
	 * @param vl
	 * @param vr
	 */
	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW mw, ResponseI res, MessageContext hc,
			ValidateResult<MessageI> vr) {
		MessageI req = mw.getMessage();
		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		// here the data is valid for save processing.
		String email = (String) req.getPayload("email");// this is account
		email = email.toLowerCase();//

		Account old = this.dataService.getNewestById(Account.class, email, false);
		if (old != null) {
			res.getErrorInfos().addError(Constants.P_ERROR_SIGNUP);
			return;
		}
		String nick = (String) req.getPayload("nick");// just for display.
		String pass = (String) req.getPayload("password");
		// TODO query by email.
		// TODO query to assert there is no pending signup or account.

		String passcode = nick;// TODO generate a passcode.
		//
		String confirmCode = UUID.randomUUID().toString();

		SignupRequest pts = new SignupRequest().forCreate(this.dataService);// NOTE
		pts.setEmail(email);//
		pts.setPassword(pass);
		pts.setNick(nick);
		pts.setConfirmCode(confirmCode);//
		pts.save(true);

		// this.confirmCodeNotifier.notify(mw, hc, email, confirmCode);
		SignupHandler.confirm(this.dataService, email, confirmCode, res.getErrorInfos());
	}

	public static void confirm(DataServiceI ds, String email, String confirmCode, ErrorInfos eis) {
		email = email.toLowerCase();
		NodeSearchOperationI<SignupRequest> qo = ds.prepareNodeSearch(NodeTypes.SIGNUP_REQUEST);

		qo.propertyEq(SignupRequest.PK_CONFIRM_CODE, confirmCode);
		qo.propertyEq(SignupRequest.PK_EMAIL, email);// query by email and the
		// confirm code.

		NodeSearchResultI<SignupRequest> rst = qo.execute().getResult().assertNoError().cast();
		List<SignupRequest> srl = rst.list();
		if (srl.isEmpty()) {
			eis.add(new ErrorInfo(null, "confirmCode or username error,or already confirmed."));// TODO
			return;
		}
		SignupRequest sp = srl.get(0);//

		String password = (String) sp.getProperty("password");

		// do really create account.
		Account an = new Account().forCreate(ds);
		an.setId(email);// email as the id?
		an.setPassword(password);
		an.setNick(sp.getNick());
		an.setType(Account.TYPE_REGISTERED);
		an.save(true);
		//
		AccountInfo xai = new AccountInfo().forCreate(ds);
		xai.setId(email);
		xai.setEmail(email);//
		xai.setAccountId(an.getId());
		xai.setPassword(password);//
		xai.save(true);//
	}

	@Handle("anonymous")
	// create anonymous account.
	public void handleAnonymous(MessageContext hc, MessageI req, ResponseI res) {
		String id = UUID.randomUUID().toString();
		Account an = new Account().forCreate(this.dataService);

		an.setId(id);//
		an.setType(Account.TYPE_ANONYMOUS);
		an.setPassword(id);//
		an.setNick(id);
		an.save(true);

		res.setPayload("accountId", an.getId());
		res.setPayload("password", an.getPassword());

	}
}
