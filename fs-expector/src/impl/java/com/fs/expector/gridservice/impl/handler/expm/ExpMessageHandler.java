/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.expm;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpMessageHandler extends ExpectorTMREHSupport {

	private CodecI codec;

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

		this.codec = this.getContainer().getTop().find(CodecI.FactoryI.class, true)
				.getCodec(PropertiesI.class);
	}

	private PropertiesI<Object> decodeMessageExtend(String body) {
		JSONArray jsn;
		try {
			jsn = (JSONArray) new JSONParser().parse(body);
		} catch (ParseException e) {
			throw new FsException(e);
		}

		PropertiesI<Object> rt = (PropertiesI<Object>) this.codec.decode(jsn);
		return rt;
	}

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

		String accountId2 = req.getString("accountId2", false);
		String expId2 = req.getString("expId2", false);

		NodeQueryOperationI<ExpMessage> qo = this.dataService.prepareNodeQuery(ExpMessage.class);

		qo.first(0);
		qo.maxSize(Integer.MAX_VALUE);
		if (expId2 != null) {
			qo.propertyEq(ExpMessage.EXP_ID2, expId2);
		}
		if (accountId2 != null) {
			qo.propertyEq(ExpMessage.ACCOUNT_ID2, accountId2);
		}
		// qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeQueryResultI<ExpMessage> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());

	}

	private void processExpsResult(ResponseI res, List<ExpMessage> list) {
		// convert
		
		List<MessageI> ml = new ArrayList<MessageI>();
		for (ExpMessage em : list) {
			MessageI msg = new MessageSupport(em.getPath());
			msg.setHeader("id", em.getId());
			msg.setHeader("accountId1", em.getAccountId1());
			msg.setHeader("accountId2", em.getAccountId2());
			msg.setHeader("expId1", em.getExpId1());
			msg.setHeader("expId2", em.getExpId2());
			
			
			String accId1 = em.getAccountId1();
			
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId1, true);
			
			msg.setPayload("nick1", acc.getNick());
			String body = em.getBody();
			
			PropertiesI<Object> pls = this.decodeMessageExtend(body);
			msg.setPayloads(pls);
			ml.add(msg);
		}

		res.setPayload("expMessages", ml);
	}
}