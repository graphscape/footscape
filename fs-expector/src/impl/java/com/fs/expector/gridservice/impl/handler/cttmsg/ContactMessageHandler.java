/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.cttmsg;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.expector.dataservice.api.wrapper.ContactMessage;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu <br>
 *         Leave a message to us.
 */
public class ContactMessageHandler extends ExpectorTMREHSupport {
	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		{
			ValidatorI<MessageI> vl = this.createValidator("submit");
			vl.addExpression(Constants.P_ERROR_CTTMSG_MESSAGE_SUBJECT, prefix + "['subject']!=null");
			vl.addExpression(Constants.P_ERROR_CTTMSG_MESSAGE_NAME, prefix + "['name']!=null");
			vl.addExpression(Constants.P_ERROR_CTTMSG_MESSAGE_EMAIL, prefix + "['email']!=null");
			vl.addExpression(Constants.P_ERROR_CTTMSG_MESSAGE_BODY, prefix + "['body']!=null");
			// passcode in session .
			// vl.addExpression("payloads.property['passcode']==property['session'].property['passcode']");
		}

		// this.confirmCodeNotifier =
		// this.top.finder(ConfirmCodeNotifierI.class).name("main").find(true);
	}

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res,
			ValidateResult<MessageI> vl) {
		if (res.getErrorInfos().hasError()) {
			return;

		}

		MessageI req = ew.getMessage();//
		String body = (String) req.getPayload("body", true);
		String subject = (String) req.getPayload("subject", true);
		String name = (String) req.getPayload("name", true);
		String email = (String) req.getPayload("email", true);

		ContactMessage cms = new ContactMessage().forCreate(this.dataService);
		cms.setSubject("n/a");//
		cms.setName(name);
		cms.setEmail(email);
		cms.setSubject(subject);
		cms.setBody(body);
		cms.save(true);

		String eid = cms.getId();

		res.setPayload("cttMsgId", eid);//
	}

}
