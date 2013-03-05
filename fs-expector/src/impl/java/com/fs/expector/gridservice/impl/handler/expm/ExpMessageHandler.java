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
		res.setPayload("expMessageId",id);
	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		Integer from = (Integer) req.getPayload("firstResult", true);
		Integer max = (Integer) req.getPayload("maxResult", true);
		boolean includeMe = req.getBoolean("includeMine", true);
		int slop = (Integer) req.getPayload("slop", 0);

		String expId = (String) req.getPayload("expId");// may null

		String phrase = (String) req.getPayload("phrase");// may null

		NodeQueryOperationI<Expectation> qo = this.dataService.prepareNodeQuery(Expectation.class);

		qo.first(from);
		qo.maxSize(max);
		if (!includeMe) {
			String thisAccId = this.getAccountId(ew, true);//
			qo.propertyNotEq(Expectation.ACCOUNT_ID, thisAccId);
		}
		qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeQueryResultI<Expectation> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());

	}

	private void processExpsResult(ResponseI res, List<Expectation> list) {
		// convert
		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(list, new String[] { NodeI.PK_ID,
				Expectation.BODY, NodeI.PK_TIMESTAMP, Expectation.ACCOUNT_ID },//
				new boolean[] { true, true, true, true }, // force
				new String[] { "id", "body", "timestamp", "accountId" }//
				);
		for (PropertiesI<Object> pts : el) {
			String accId = (String) pts.getProperty("accountId");
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId, true);
			pts.setProperty("nick", acc.getNick());

			// profile may not exist.

			Profile pf = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId, false);
			String icon = pf == null ? null : pf.getIcon();
			if (icon == null) {
				icon = this.config.getProperty("defaultIconDataUrl");//
			}
			pts.setProperty("iconDataUrl", icon);//

		}

		res.setPayload("expectations", el);
	}
}