/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.handler.password;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.mail.MailSenderI;
import com.fs.commons.api.mail.MimeMessageWrapper;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.PasswordForgot;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class PasswordHandler extends ExpectorTMREHSupport {

	private static Logger LOG = LoggerFactory.getLogger(PasswordHandler.class);

	private MailSenderI mailSender;

	private String rootUrl;

	public PasswordHandler() {
		super();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		this.mailSender = this.top.find(MailSenderI.class, true);
		this.rootUrl = this.config.getProperty("rootUrl", true);

		{
			ValidatorI<MessageI> vl = this.createValidator("forgot");
			vl.addExpression(Constants.P_ERROR_PASSWORD_EMAIL, prefix + "['email']!=null");
			// passcode in session .
			// vl.addExpression("payloads.property['passcode']==property['session'].property['passcode']");
		}
		{
			ValidatorI<MessageI> vl = this.createValidator("reset");
			vl.addExpression(Constants.P_ERROR_PASSWORD_PFID, prefix + "['pfId']!=null");
			vl.addExpression(Constants.P_ERROR_PASSWORD_NEWPASSWORD, prefix + "['newPassword']!=null");

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

	@Handle("reset")
	public void handleReset(TerminalMsgReceiveEW mw, ResponseI res, MessageContext hc,
			ValidateResult<MessageI> vr) {
		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		MessageI req = mw.getMessage();
		String pfId = req.getString("pfId", true);
		String pass = req.getString("newPassword", true);

		PasswordForgot pf = this.dataService.getNewestById(PasswordForgot.class, pfId, false);
		if (pf == null) {
			res.getErrorInfos().addError(Constants.P_ERROR_PASSWORD_FORGOT_RESET);//
			return;
		}
		String aid = pf.getAccountId();
		this.efacade.updatePassword(aid, pass);
		this.dataService.deleteById(PasswordForgot.class, pfId);//
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
	@Handle("forgot")
	public void handleForgot(TerminalMsgReceiveEW mw, ResponseI res, MessageContext hc,
			ValidatorI<MessageI> vl, ValidateResult<MessageI> vr) {
		MessageI req = mw.getMessage();
		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}

		// here the data is valid for save processing.
		String email = (String) req.getPayload("email", true);// this is account
		email = email.toLowerCase();//

		Account a = this.efacade.getAccountByEmail(email);

		if (a == null) {
			res.getErrorInfos().addError("error/password-forgot/failure");
			return;
		}
		String aid = a.getId();
		String pid = UUID.randomUUID().toString();//
		PasswordForgot pf = new PasswordForgot().forCreate(this.dataService);
		pf.setId(pid);
		pf.setAccountId(aid);
		pf.save(true);
		//
		// TODO security by create a temp record and delete it when click this
		// url:
		String resetUrl = this.getResetUrl(pid);

		MimeMessageWrapper mm = this.mailSender.createMimeMessage();
		mm.setSubject("Your password on Waitee.");
		mm.setTo(email);
		// mm.setFrom(from);
		mm.setText("<h>Hello," + a.getNick() + "</h>"
				+ //
				"<p> We'v told that you have forgot your password on Waitee, please <a href='" + resetUrl
				+ "'> click here </a> to reset your password!</p>" + //
				//
				"", true);
		this.mailSender.send(mm);//
	}

	private String getResetUrl(String pid) {
		String rt = this.rootUrl;
		if (rt.contains("?")) {
			rt += "&";
		} else {
			rt += "?";
		}

		rt += "action=pf&pfId=" + pid;

		return rt;
	}
}
