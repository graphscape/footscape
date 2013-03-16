/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.expe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.handler.expm.ExpMessageHandler;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpEditHandler extends ExpectorTMREHSupport {

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {

		// the relation between activity and user.
		SessionGd s = this.getSession(ew, true);
		String aid = s.getAccountId();

		boolean isa = (Boolean) s.getProperty("isAnonymous", true);
		if (isa) {
			throw new FsException("operation not allowed for anonymous user");
		}

		MessageI req = ew.getMessage();//
		String body = (String) req.getPayload("body", true);

		Expectation exp = new Expectation().forCreate(this.dataService);

		exp.setAccountId(aid);
		exp.setBody(body);
		exp.save(true);

		String eid = exp.getId();

		res.setPayload("expId", eid);//
	}

	@Handle("close")
	public void handleClose(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {

		// the relation between activity and user.
		SessionGd s = this.getSession(ew, true);
		String aid = s.getAccountId();

		MessageI req = ew.getMessage();//

		String expId = (String) req.getPayload("expId", true);

		//
		// TODO delete by query;
		List<Connection> cL = ExpMessageHandler.getConnectionList(this.dataService, expId);
		Set<String> accIdSet = new HashSet<String>();
		accIdSet.add(aid);//self notify
		for (Connection c : cL) {
			this.dataService.deleteById(Connection.class, c.getId());
			String accId = c.getAccountId1();
			accIdSet.add(accId);
		}
		this.dataService.deleteById(Expectation.class, expId);
		for (String accId : accIdSet) {
			MessageI msg = new MessageSupport("/notify/exp-deleted");
			msg.setHeader("expId", expId);
			this.onlineNotifyService.tryNotifyAccount(accId, msg);
		}
		// delete exp.

	}
}
