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
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.handler.cooper.CooperHandler;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpMessageHandler extends ExpectorTMREHSupport {

	private CodecI codec;

	private int maxSizeOfMessageQuery = 10000;

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
	private String encodeMessageExtend(PropertiesI<Object> body){
		JSONArray ja = (JSONArray) this.codec.encode(body);
		return ja.toJSONString();
	}
	@Handle("create")
	public void handleCreate(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId1 = req.getString("expId1", true);
		String expId2 = req.getString("expId2", true);
		String path = req.getString("path",true);
		String header = req.getString("header", true);
		
		PropertiesI<Object> body = (PropertiesI<Object>) req.getPayload("body",false);
		
		String bodyS = body == null?null:this.encodeMessageExtend(body);
		
		Expectation exp1 = this.dataService.getNewestById(Expectation.class, expId1, true);
		Expectation exp2 = this.dataService.getNewestById(Expectation.class, expId2, true);

		String accId1 = exp1.getAccountId();
		String accId2 = exp2.getAccountId();

		ExpMessage em = new ExpMessage().forCreate(this.dataService);
		em.setExpId1(expId1);
		em.setExpId2(expId2);
		em.setAccountId1(accId1);
		em.setAccountId2(accId2);
		em.setPath(path);
		em.setHeader(header);
		em.setBody(bodyS);
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
		qo.maxSize(this.maxSizeOfMessageQuery);
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
			String expId1 = em.getExpId1();
			String expId2 = em.getExpId2();
			String path = em.getPath();
			MessageI msg = new MessageSupport(path);
			msg.setHeader("id", em.getId());
			msg.setHeader("accountId1", em.getAccountId1());
			msg.setHeader("accountId2", em.getAccountId2());
			msg.setHeader("expId1", em.getExpId1());
			msg.setHeader("expId2", em.getExpId2());

			{// account
				String accId1 = em.getAccountId1();
				// account must be exist
				Account acc = this.dataService.getNewestById(Account.class, accId1, true);

				msg.setPayload("nick1", acc.getNick());
			}
			{// exp1 body
				Expectation exp1 = this.getExpectation(expId1);
				msg.setPayload("expBody1", exp1.getBody());

			}
			{// exp2 body
				Expectation exp2 = this.getExpectation(expId2);
				msg.setPayload("expBody2", exp2.getBody());

			}
			{// body,extends properties
				String body = em.getBody();
				PropertiesI<Object> pls = this.decodeMessageExtend(body);
				msg.setPayloads(pls);
				// is a cooper request message,add extends field
				if (CooperHandler.MP_CONNECT_REQUEST.equals(path)) {//
					String crId = (String) pls.getProperty("cooperRequestId", true);
					ConnectRequest cr = this.dataService.getNewestById(ConnectRequest.class, crId, false);
					msg.setPayload("cooperRequest", cr == null ? null : cr.getTarget());
				}
			}
			ml.add(msg);
		}

		res.setPayload("expMessages", ml);
	}

	private Expectation getExpectation(String expId) {
		return this.dataService.getNewestById(Expectation.class, expId, true);
	}
}