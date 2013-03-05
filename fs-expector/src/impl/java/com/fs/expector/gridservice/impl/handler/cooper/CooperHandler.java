/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.cooper;

import java.util.List;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
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
		ConnectRequest cr = new ConnectRequest().forCreate(this.dataService);
		cr.setAccountId1(aid);
		cr.setExpId1(expId1);
		cr.setExpId2(expId2);
		cr.setAccountId2(accId2);
		cr.save(true);

		String cid = cr.getId();
		//

		res.setPayload("cooperRequestId", cid);//
		// notify the exp2's account to refresh the incoming request,if he/she
		// is online
		
		//TODO move below code to a service: ExpMessageServiceI?
		ExpMessage em = new ExpMessage().forCreate(this.dataService);
		em.setExpId1(expId1);
		em.setExpId2(expId2);
		em.setAccountId1(aid);
		em.setAccountId2(accId2);
		em.setPath("/connect/request");
		em.setHeader("");
		em.setBody("exp2.body:"+exp2.getBody());
		em.save(true);
		
		MessageI msg = new MessageSupport("/notify/exp-message-created");

		this.onlineNotifyService.tryNotifyAccount(accId2, msg);
	}

	@Handle("confirm")
	public void handleConfirm(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();//
		String crid = (String) req.getPayload("cooperRequestId", true);

		ConnectRequest cr = this.dataService.getNewestById(ConnectRequest.class, crid, true);
		Connection c = new Connection().forCreate(this.dataService);
		c.setAccountId1(cr.getAccountId1());
		c.setAccountId2(cr.getAccountId2());
		c.setExpId1(cr.getExpId1());
		c.setExpId2(cr.getExpId2());
		c.save(true);

		this.dataService.deleteById(ConnectRequest.class, crid);// delete this
																// cr,if

		res.setPayload("crId", crid);//
	}

	// TODO replace by server notifier to client.
	@Handle("incomingCr")
	public void handleRefreshIncomingCr(TerminalMsgReceiveEW ew, ResponseI res) {

		String accId = this.getAccountId(ew, true);

		List<ConnectRequest> crL = this.dataService.getListNewestFirst(ConnectRequest.class,
				ConnectRequest.ACCOUNT_ID2, accId, 0, Integer.MAX_VALUE);
		List<PropertiesI<Object>> ptsL = NodeWrapperUtil.convert(crL);
		res.setPayload("cooperRequestList", ptsL);
	}

}
