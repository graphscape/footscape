/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.expc;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.dataservice.impl.ExpectorDsFacadeImpl;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpConnectHandler extends ExpectorTMREHSupport {

	private CodecI codec;

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

		this.codec = this.getContainer().getTop().find(CodecI.FactoryI.class, true)
				.getCodec(PropertiesI.class);
	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		String accountId1 = req.getString("accountId1", true);

		this.assertAccout(ew, accountId1, res.getErrorInfos());
		if (res.getErrorInfos().hasError()) {
			return;
		}

		String expId1 = req.getString("expId1", false);

		NodeSearchOperationI<Connection> qo = this.dataService.prepareNodeSearch(Connection.class);

		qo.first(0);
		qo.maxSize(ExpectorDsFacadeImpl.maxSizeOfConnectQuery);// TODO
																// application
																// determine
		// this?
		qo.propertyEq(Connection.ACCOUNT_ID1, accountId1);
		if (expId1 != null) {
			qo.propertyEq(Connection.EXP_ID1, expId1);
		}

		// qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeSearchResultI<Connection> rst = qo.execute().getResult().assertNoError();
		/*
		 * List<PropertiesI<Object>> el = NodeWrapperUtil.convert(rst.list(),
		 * new String[] { NodeI.PK_ID, Connection.ACCOUNT_ID1,
		 * Connection.ACCOUNT_ID2, Connection.EXP_ID1, Connection.EXP_ID2 },//
		 * 
		 * new boolean[] { true, true, true, true, true }, // force new String[]
		 * { "id", "accountId1", "accountId2", "expId1", "expId2" }// );
		 */
		List<PropertiesI<Object>> el = new ArrayList<PropertiesI<Object>>();

		for (Connection c : rst.list()) {
			PropertiesI<Object> pts = c.getTarget();
			{
				String expId2 = c.getExpId2();
				Expectation exp2 = this.getExpectation(expId2);
				pts.setProperty("expBody2", exp2.getBody());
				pts.setProperty("expTitle2", exp2.getTitle());
			}//

			{
				String accId2 = c.getAccountId2();
				Account a = this.dataService.getNewestById(Account.class, accId2, true);
				pts.setProperty("nick2", a.getNick());

			}
			{
				String accId1 = c.getAccountId1();
				Profile p = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId1, false);

				String icon1 = p == null ? null : p.getIcon();
				pts.setProperty("icon1", icon1);
			}
			el.add(pts);
		}

		res.setPayload("expConnectList", el);
	}

	private Expectation getExpectation(String expId) {
		return this.dataService.getNewestById(Expectation.class, expId, true);
	}

}