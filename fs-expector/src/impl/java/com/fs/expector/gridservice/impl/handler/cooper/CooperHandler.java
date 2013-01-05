/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.cooper;

import java.util.List;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.Activity;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.dataservice.api.wrapper2.UserActivity;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class CooperHandler extends ExpectorTMREHSupport {
	// query activities by account.

	@Handle("request")
	public void handleRequest(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {

		MessageI req = ew.getMessage();//

		String aid = this.getSession(ew, true).getAccountId();//
		// the relation between activity and user.
		String expId1 = (String) req.getPayload("expId1", true);
		String expId2 = (String) req.getPayload("expId2", true);

		Expectation exp2 = this.dataService.getNewestById(Expectation.class, expId2, true);
		String accId2 = exp2.getAccountId();
		CooperRequest cr = new CooperRequest().forCreate(this.dataService);
		cr.setAccountId1(aid);
		cr.setExpId1(expId1);
		cr.setExpId2(expId2);
		cr.setAccountId2(accId2);
		cr.save(true);

		String cid = cr.getId();
		//

		res.setPayload("cooperMessageId", cid);//
		// notify the exp2's account to refresh the incoming request,if he/she
		// is online
		SessionGd s2 = this.sessionManager.getEntityByField(SessionGd.ACCID, accId2, false);

		if (s2 != null) {// not online
			TerminalGd t2 = this.terminalManager.getTerminalBySessionId(s2.getId(), false);
			if (t2 == null) {// TODO
				throw new FsException("TODO,remove old session when binding new session.");
			}

			MessageI msg = new MessageSupport();
			msg.setHeader(MessageI.HK_PATH, "/notify/incomingCr");

			this.terminalManager.sendMessage(t2.getId(), msg);

		}
	}

	@Handle("confirm")
	public void handleConfirm(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();//
		String crid = (String) req.getPayload("cooperMessageId", true);

		boolean findAct = req.getPayload(Boolean.class, "useNewestActivityId", Boolean.FALSE);//
		CooperRequest cr = this.dataService.getNewestById(CooperRequest.class, crid, true);
		String exp2ActId = null;
		if (findAct) {// find the newest activity for the expId2,in case there
						// is already one activity for it.

			ExpActivity ea = this.dataService.getNewest(ExpActivity.class, ExpActivity.PK_EXP_ID,
					cr.getExpId2(), false);
			if (ea != null) {
				exp2ActId = ea.getActivityId();//

			}
		}

		String actId = exp2ActId;

		Expectation exp1 = this.dataService.getNewestById(Expectation.class, cr.getExpId1(), true);
		Expectation exp2 = this.dataService.getNewestById(Expectation.class, cr.getExpId2(), true);

		if (actId == null) {// create a new activity.
			Activity act = new Activity().forCreate(this.dataService);

			act.save(true);

			actId = act.getId();//

			// TODO move this creating to other more reliable machanism

			// relate activity with exp2,exp2's user create this activity.
			this.tryLinkExp2Activity(exp2, actId);

		}
		// relate activity with exp1,exp1 request for cooper and activity is
		// allowed for his enter.
		this.tryLinkExp2Activity(exp1, actId);

		this.dataService.deleteById(CooperRequest.class, crid);// delete this
																// cr,if

		// res.setPayload("cooperConfirmId", ccid);//
	}

	protected void tryLinkExp2Activity(Expectation exp, String actId) {
		ExpActivity oldEa = this.dataService.getNewest(ExpActivity.class, new String[] {
				ExpActivity.PK_EXP_ID, ExpActivity.PK_ACTIVITY_ID }, new Object[] { exp.getId(), actId },
				false);
		if (oldEa == null) {
			ExpActivity ea = new ExpActivity().forCreate(this.dataService);
			ea.setAccountId(exp.getAccountId());
			ea.setExpId(exp.getId());//
			ea.setActivityId(actId);
			ea.save(true);// Note the last one is refresh
			// query the user's activity list,update the link
		}
		UserActivity oldUa = this.dataService.getNewest(UserActivity.class, new String[] {
				UserActivity.PK_ACCOUNT_ID, UserActivity.PK_ACTIVITY_ID }, new Object[] { exp.getAccountId(),
				actId }, false);
		if (oldUa == null) {
			// TODO duplicated entry:
			UserActivity ua = new UserActivity().forCreate(this.dataService);
			ua.setAccountId(exp.getAccountId());// the exp's user
			ua.setActivityId(actId);
			ua.save(true);
		}
	}

	// TODO replace by server notifier to client.
	@Handle("incomingCr")
	public void handleRefreshIncomingCr(TerminalMsgReceiveEW ew, ResponseI res) {

		String accId = this.getAccountId(ew, true);

		List<CooperRequest> crL = this.dataService.getListNewestFirst(CooperRequest.class,
				CooperRequest.ACCOUNT_ID2, accId, 0, Integer.MAX_VALUE);
		List<PropertiesI<Object>> ptsL = NodeWrapperUtil.convert(crL);
		res.setPayload("cooperRequestList", ptsL);
	}

}
