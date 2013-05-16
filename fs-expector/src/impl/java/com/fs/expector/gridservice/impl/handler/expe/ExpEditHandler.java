/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.expe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.util.ImageUrl;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.handler.expm.ExpMessageHandler;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpEditHandler extends ExpectorTMREHSupport {
	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		{
			ValidatorI<MessageI> vl = this.createValidator("submit");
			vl.addExpression(Constants.P_ERROR_EXPE_SUBMIT_TITLE, prefix + "['title']!=null");
			vl.addExpression(Constants.P_ERROR_EXPE_SUBMIT_SUMMARY, prefix + "['summary']!=null");
			vl.addExpression(Constants.P_ERROR_EXPE_SUBMIT_FORMAT, prefix + "['format']!=null");
			vl.addExpression(Constants.P_ERROR_EXPE_SUBMIT_BODY, prefix + "['body']!=null");
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
		// the relation between activity and user.
		SessionGd s = this.getSession(ew, true);
		String aid = s.getAccountId();

		boolean isa = (Boolean) s.getProperty("isAnonymous", true);
		if (isa) {
			throw new FsException("operation not allowed for anonymous user");
		}
		//the max limit
		
		int eoc = this.efacade.getExpectationOpenCount(aid);
		int limit = this.efacade.getMaxLimitOfExpectationOpenCount();
		if(eoc >= limit){
			res.getErrorInfos().add(new ErrorInfo(Constants.P_ERROR_EXP_OVERFLOW));
			return ;
		}
		//

		MessageI req = ew.getMessage();//
		String body = (String) req.getPayload("body", true);
		String title = (String) req.getPayload("title", true);
		String format = (String) req.getPayload("format", true);
		String summary = (String) req.getPayload("summary", true);
		String icon = (String) req.getPayload("icon", ImageUrl.NONE.toString());//no use
		String imageS = (String) req.getPayload("image", ImageUrl.NONE.toString());//
		ImageUrl image = ImageUrl.parse(imageS, true);
		
		image = this.efacade.saveImage(image);
		
		Expectation exp = new Expectation().forCreate(this.dataService);

		exp.setStatus(Expectation.ST_OPEN);
		exp.setAccountId(aid);
		exp.setTitle(title);
		exp.setSummary(summary);
		exp.setFormat(format);
		exp.setBody(body);
		exp.setIcon(icon);
		exp.setImage(image.toString());
		exp.save(true);

		String eid = exp.getId();

		res.setPayload("expId", eid);//
	}

	@Handle("close")
	public void handleClose(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		SessionGd s = this.getSession(ew, true);
		String aid = s.getAccountId();

		MessageI req = ew.getMessage();//

		String expId = (String) req.getPayload("expId", true);
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, true);
		String uid = exp.getUniqueId();
		// close exp
		this.dataService.updateByUid(Expectation.class, uid, Expectation.STATUS, Expectation.ST_CLOSE);
		{//notify
			MessageI msg = new MessageSupport("/notify/exp-closed");
			msg.setHeader("expId", expId);
			this.onlineNotifyService.tryNotifyAccount(aid, msg);
			
		}
	}

	@Handle("delete")
	public void handleDelete(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {

		// the relation between activity and user.
		SessionGd s = this.getSession(ew, true);
		String aid = s.getAccountId();

		MessageI req = ew.getMessage();//

		String expId = (String) req.getPayload("expId", true);

		// delete connection to and from this exp;
		// TODO delete by query;
		List<Connection> cL = ExpMessageHandler.getConnectionList(this.dataService, expId);
		Set<String> accIdSet = new HashSet<String>();
		accIdSet.add(aid);// self notify
		for (Connection c : cL) {
			this.dataService.deleteById(Connection.class, c.getId());
			String accId = c.getAccountId1();
			accIdSet.add(accId);
		}
		// delete message to this exp
		ExpMessageHandler.deleteMessageByExpId2(this.dataService, expId);

		// message from this exp not delete?

		// delete exp.

		this.dataService.deleteById(Expectation.class, expId);

		// notify
		for (String accId : accIdSet) {
			MessageI msg = new MessageSupport("/notify/exp-deleted");
			msg.setHeader("expId", expId);
			this.onlineNotifyService.tryNotifyAccount(accId, msg);
		}

	}
}
