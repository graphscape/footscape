/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.expm;

import java.util.List;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpMessageHandler extends ExpectorTMREHSupport {

	@Handle("create")
	public void handleCreate(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId1 = req.getString("expId1", true);
		String expId2 = req.getString("expId2", true);
		String header = req.getString("header", true);
		String body = req.getString("body", true);

		Expectation exp1 = this.dataService.getNewestById(Expectation.class, expId1, true);
		Expectation exp2 = this.dataService.getNewestById(Expectation.class, expId2, true);

		String accId1 = exp1.getAccountId();
		String accId2 = exp2.getAccountId();

		ExpMessage em = new ExpMessage().forCreate(this.dataService);
		em.setExpId1(expId1);
		em.setExpId2(expId2);
		em.setAccountId1(accId1);
		em.setAccountId2(accId2);
		em.setHeader(header);
		em.setBody(body);
		em.save(true);
		String id = em.getId();
		res.setPayload("expMessageId", id);
	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		String expId2 = req.getString("expId2", true);

		NodeQueryOperationI<ExpMessage> qo = this.dataService.prepareNodeQuery(ExpMessage.class);

		qo.first(0);
		qo.maxSize(Integer.MAX_VALUE);
		qo.propertyEq(ExpMessage.EXP_ID2, expId2);
		// qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeQueryResultI<ExpMessage> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());

	}

	private void processExpsResult(ResponseI res, List<ExpMessage> list) {
		// convert
		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(list, new String[] { NodeI.PK_ID,
				ExpMessage.HEADER, ExpMessage.BODY, NodeI.PK_TIMESTAMP, ExpMessage.EXP_ID1,
				ExpMessage.ACCOUNT_ID1 },//
				new boolean[] { true, true, true, true, true, true }, // force
				new String[] { "id", "header", "body", "timestamp", "expId1", "accountId1" }//
				);

		for (PropertiesI<Object> pts : el) {
			String accId1 = (String) pts.getProperty("accountId1");
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId1, true);
			pts.setProperty("nick1", acc.getNick());

		}

		res.setPayload("expMessages", el);
	}
}