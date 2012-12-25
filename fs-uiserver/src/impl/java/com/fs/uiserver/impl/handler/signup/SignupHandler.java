/**
 * Jun 22, 2012
 */
package com.fs.uiserver.impl.handler.signup;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.NodeTypes;
import com.fs.dataservice.api.expapp.wrapper.Account;
import com.fs.dataservice.api.expapp.wrapper.AccountInfo;
import com.fs.dataservice.api.expapp.wrapper.SignupRequest;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.api.signup.ConfirmCodeNotifierI;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class SignupHandler extends UiHandlerSupport {

	private static Logger LOG = LoggerFactory.getLogger(SignupHandler.class);

	protected ConfirmCodeNotifierI confirmCodeNotifier;

	public SignupHandler() {
		super();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		{
			ValidatorI<RequestI> vl = this.createValidator("submit");
			vl.addExpression("payloads.property['email']!=null");
			vl.addExpression("payloads.property['password']!=null");
			vl.addExpression("payloads.property['nick']!=null");
			vl.addExpression("payloads.property['isAgree']");
			// passcode in session .
			//vl.addExpression("payloads.property['passcode']==property['session'].property['passcode']");
		}
		{
			ValidatorI<RequestI> vl = this.createValidator("confirm");
			vl.addExpression("payloads.property['email']!=null");
			vl.addExpression("payloads.property['confirmCode']!=null");
		}

		this.confirmCodeNotifier = this.container
				.finder(ConfirmCodeNotifierI.class).name("main").find(true);

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

	/* */
	@Override
	public void handle(HandleContextI sc) {

		super.handle(sc);
	}

	@Handle("init")
	public void handleInit(RequestI req, ResponseI res, HandleContextI hc) {

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
	public void handleSubmit(RequestI req, ResponseI res, HandleContextI hc,
			ValidatorI<RequestI> vl, ValidateResult<RequestI> vr) {

		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		// here the data is valid for save processing.
		String email = (String) req.getPayload("email");// this is account
		email = email.toLowerCase();//
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
		this.confirmCodeNotifier.notify(hc, email, confirmCode);

	}

	/**
	 * finish signup process,user who submit the information input the confirm
	 * code that received by mail address.
	 * 
	 * @param hc
	 * @param req
	 * @param res
	 */
	@Handle("confirm")
	public void handleConfirm(HandleContextI hc, RequestI req, ResponseI res) {
		String email = (String) req.getPayload("email");
		email = email.toLowerCase();
		String confirmCode = (String) req.getPayload("confirmCode");
		NodeQueryOperationI<SignupRequest> qo = this.dataService
				.prepareNodeQuery(NodeTypes.SIGNUP_REQUEST);

		qo.propertyEq(SignupRequest.PK_CONFIRM_CODE, confirmCode);
		qo.propertyEq(SignupRequest.PK_EMAIL, email);// query by email and the
		// confirm code.

		NodeQueryResultI<SignupRequest> rst = qo.execute().getResult()
				.assertNoError().cast();
		List<SignupRequest> srl = rst.list();
		if (srl.isEmpty()) {
			res.getErrorInfos()
					.add(new ErrorInfo(null,
							"confirmCode or username error,or already confirmed."));// TODO
			return;
		}
		SignupRequest sp = srl.get(0);//

		String password = (String) sp.getProperty("password");

		// do really create account.
		Account an = new Account().forCreate(this.dataService);
		an.setId(email);// email as the id?
		an.setPassword(password);
		an.setNick(sp.getNick());
		an.setIsAnonymous(false);
		an.save(true);
		//
		AccountInfo xai = new AccountInfo().forCreate(this.dataService);
		xai.setId(email);
		xai.setEmail(email);//
		xai.setAccountId(an.getId());
		xai.setPassword(password);//
		xai.save(true);//
	}

}
