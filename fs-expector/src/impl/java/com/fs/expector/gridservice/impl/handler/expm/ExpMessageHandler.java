/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.expm;

import java.util.ArrayList;
import java.util.Date;
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
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.handler.cooper.CooperHandler;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpMessageHandler extends ExpectorTMREHSupport {

	private CodecI codec;
	public int defaultLimit = 50;

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

	private String encodeMessageExtend(PropertiesI<?> body) {
		JSONArray ja = (JSONArray) this.codec.encode(body);
		return ja.toJSONString();
	}

	/**
	 * expId1 is from exp,expId2 is to exp, if to exp is null, broadcast this
	 * message to all expId1's connected exps. Mar 16, 2013
	 */
	@Handle("create")
	public void handleCreate(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId1 = req.getString("expId1", true);
		Expectation exp1 = this.dataService.getNewestById(Expectation.class, expId1, true);

		String path = req.getString("path", true);

		PropertiesI<String> header = (PropertiesI<String>) req.getPayload("header", false);

		PropertiesI<Object> body = (PropertiesI<Object>) req.getPayload("body", false);

		String bodyS = body == null ? null : this.encodeMessageExtend(body);
		String headerS = header == null ? null : this.encodeMessageExtend(header);

		List<String> expId2L = new ArrayList<String>();

		{
			String expId2 = req.getString("expId2", false);

			if (expId2 == null) {// broad cast
				expId2L.add(expId1);
				List<String> idL = getConnectedExpId2List(this.dataService, expId1);
				expId2L.addAll(idL);
			} else {// send to
				expId2L.add(expId2);
			}
		}

		for (String expId2 : expId2L) {

			Expectation exp2 = this.dataService.getNewestById(Expectation.class, expId2, false);

			String accId1 = exp1.getAccountId();
			String accId2 = exp2.getAccountId();

			ExpMessage em = new ExpMessage().forCreate(this.dataService);
			em.setExpId1(expId1);
			em.setExpId2(expId2);
			em.setAccountId1(accId1);
			em.setAccountId2(accId2);
			em.setPath(path);
			em.setHeader(headerS);
			em.setBody(bodyS);
			em.save(true);
			// todo async notify
			MessageI msg = new MessageSupport("/notify/exp-message-created");
			msg.setHeader("expId2", expId2);
			msg.setHeader("expId1", expId1);
			this.onlineNotifyService.tryNotifyAccount(accId2, msg);
		}

	}

	// TODO delete by query
	public static void deleteMessageByExpId2(DataServiceI ds, String expId2) {
		while (true) {
			List<ExpMessage> mL = ds
					.getListNewestFirst(ExpMessage.class, ExpMessage.EXP_ID2, expId2, 0, 1000);
			for (ExpMessage m : mL) {
				String id = m.getId();
				ds.deleteById(ExpMessage.class, id);
			}
			if(mL.isEmpty()){
				break;
			}
		}
	}

	public static List<Connection> getConnectionList(DataServiceI ds, String expId) {
		List<Connection> c1 = getConnectionListByExpId1(ds, expId);
		List<Connection> c2 = getConnectionListByExpId2(ds, expId);
		c1.addAll(c2);
		return c1;
	}

	public static List<Connection> getConnectionListByExpId1(DataServiceI ds, String expId1) {
		List<Connection> cL = ds.getListNewestFirst(Connection.class, Connection.EXP_ID1, expId1, 0, 1000);
		return cL;
	}

	public static List<Connection> getConnectionListByExpId2(DataServiceI ds, String expId2) {
		List<Connection> cL = ds.getListNewestFirst(Connection.class, new String[] { Connection.EXP_ID2 },
				new Object[] { expId2 }, 0, 1000);
		return cL;
	}

	public static List<String> getConnectedExpId2List(DataServiceI ds, String expId) {
		List<Connection> cL = ds.getListNewestFirst(Connection.class, Connection.EXP_ID1, expId, 0, 1000);
		List<String> rt = new ArrayList<String>();
		for (Connection c : cL) {
			rt.add(c.getExpId2());
		}

		return rt;
	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		String accountId2 = req.getString("accountId2", false);
		
		String expId2 = req.getString("expId2", true);
		Date timestamp1 = (Date) req.getPayload("timestamp1", false);
		Date timestamp2 = (Date) req.getPayload("timestamp2", false);
		
		Integer limit = (Integer) req.getPayload("limit", this.defaultLimit);

		NodeQueryOperationI<ExpMessage> qo = this.dataService.prepareNodeQuery(ExpMessage.class);

		if (timestamp1 != null) {
			qo.propertyGt(NodeI.PK_TIMESTAMP, timestamp1, true);
		}

		if (timestamp2 != null) {
			qo.propertyLt(NodeI.PK_TIMESTAMP, timestamp2, true);
		}
		qo.sortTimestamp(true);
		qo.first(0);
		qo.maxSize(limit);
		if (expId2 != null) {
			qo.propertyEq(ExpMessage.EXP_ID2, expId2);
		}
		if (accountId2 != null) {
			qo.propertyEq(ExpMessage.ACCOUNT_ID2, accountId2);
		}
		// qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeQueryResultI<ExpMessage> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());
		res.setPayload("limit", limit);
		res.setPayload("expId2",expId2);
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
			msg.setPayload("timestamp", em.getTimestamp());
			{// account
				String accId1 = em.getAccountId1();
				// account must be exist
				Account acc = this.dataService.getNewestById(Account.class, accId1, true);

				msg.setPayload("nick1", acc.getNick());
				String icon = this.efacade.getIconByAccountId(accId1);
				//icon of acc1
				msg.setPayload("icon1",icon);
			}
			{// exp1 body
				String expB = this.getExpBodyForMessage(expId1);
				msg.setPayload("expBody1", expB);

			}
			{// exp2 body
				String expB = this.getExpBodyForMessage(expId1);
				msg.setPayload("expBody2", expB);

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

	private String getExpBodyForMessage(String expId) {
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, false);
		return exp == null ? "Deleted" : exp.getBody();

	}
}