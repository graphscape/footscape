/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.exps;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.config.Configuration;
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
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpSearchHandler extends ExpectorTMREHSupport {

	private int defaultSearchSlop = 3;

	private String defaultExpIconDataUrl;

	/*
	 * Apr 3, 2013
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.defaultSearchSlop = cfg.getPropertyAsInt("defaultSearchSlop", 3);
		this.defaultExpIconDataUrl = this.config.getProperty("defaultExpIconDataUrl");
	}

	// query the connected exp from specified exp by exp id.
	@Handle("connected")
	public void handleConnected(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId1 = req.getString("expId1", true);
		NodeQueryOperationI<Connection> qo = this.dataService.prepareNodeQuery(Connection.class);
		qo.propertyEq(Connection.EXP_ID1, expId1);
		NodeQueryResultI<Connection> rst = qo.execute().getResult().assertNoError();

		List<Expectation> expL = new ArrayList<Expectation>();
		for (Connection c : rst.list()) {
			String id = c.getExpId2();
			Expectation exp = this.dataService.getNewestById(Expectation.class, id, true);
			expL.add(exp);
		}
		this.processExpsResult(res, expL);

	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		Integer from = (Integer) req.getPayload("firstResult", true);
		Integer max = (Integer) req.getPayload("maxResult", true);
		boolean includeMe = req.getBoolean("includeMine", true);
		int slop = (Integer) req.getPayload("slop", this.defaultSearchSlop);

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
		qo.sort(NodeI.PK_TIMESTAMP, true);
		NodeQueryResultI<Expectation> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());

	}

	private void processExpsResult(ResponseI res, List<Expectation> list) {
		// convert
		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(list, new String[] { NodeI.PK_ID,
				Expectation.BODY, NodeI.PK_TIMESTAMP, Expectation.ACCOUNT_ID, Expectation.FORMAT,
				Expectation.TITLE, Expectation.SUMMARY, Expectation.ICON },//
				new boolean[] { true, true, true, true, true, true, true, true }, // force
				new String[] { "id", "body", "timestamp", "accountId", "format", "title", "summary", "icon" }//
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
			pts.setProperty("userIcon", icon);//
			//
			this.icon(pts);
		}

		res.setPayload("expectations", el);
	}

	private void icon(PropertiesI<Object> pts) {
		String expIcon = (String) pts.getProperty("icon");
		if (expIcon.equalsIgnoreCase("n/a")) {
			expIcon = this.defaultExpIconDataUrl;
			pts.setProperty("icon", expIcon);
		}
	}

	@Handle("get")
	public void handleGet(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId = req.getString("expId", true);
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, true);
		PropertiesI<Object> pts = exp.getTarget();
		this.icon(pts);
		res.setPayload("expectation", pts);// good
	}

}