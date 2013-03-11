/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.expc;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpConnectHandler extends ExpectorTMREHSupport {

	private CodecI codec;
	
	private int maxSizeOfConnectQuery = 10000;//TODO remove this

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

		String accountId1 = req.getString("accountId1", false);
		String expId1 = req.getString("expId1", false);

		NodeQueryOperationI<Connection> qo = this.dataService.prepareNodeQuery(Connection.class);

		qo.first(0);
		qo.maxSize(this.maxSizeOfConnectQuery);//TODO application determine this?
		if (expId1 != null) {
			qo.propertyEq(ExpMessage.EXP_ID1, expId1);
		}
		if (accountId1 != null) {
			qo.propertyEq(ExpMessage.ACCOUNT_ID1, accountId1);
		}

		// qo.propertyMatch(Expectation.BODY, phrase, slop);

		NodeQueryResultI<Connection> rst = qo.execute().getResult().assertNoError();
		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(rst.list(), new String[] { NodeI.PK_ID,
				Connection.ACCOUNT_ID1, Connection.ACCOUNT_ID2, Connection.EXP_ID1, Connection.EXP_ID2 },//

				new boolean[] { true, true, true, true, true }, // force
				new String[] { "id", "accountId1", "accountId2", "expId1", "expId2" }//
				);
		res.setPayload("expConnectList", el);
	}

}