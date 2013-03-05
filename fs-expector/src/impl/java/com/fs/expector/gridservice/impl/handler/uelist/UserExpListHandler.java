/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.expector.gridservice.impl.handler.uelist;

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
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class UserExpListHandler extends ExpectorTMREHSupport {
	@Handle("init")
	public void handleInit(MessageContext hc, ResponseI res) {

	}

	/**
	 * get the detail of exp: the activity related.
	 * <p>
	 * Nov 28, 2012
	 */
	@Handle("get")
	public void handleGet(MessageContext hc, MessageI req, ResponseI res) {
		String expId = (String) req.getPayload("expId");
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, false);

		// TODO add loginId,add interceptor?

	}

	/**
	 * Refresh the summary list of ue. Nov 28, 2012
	 */
	@Handle("refresh")
	public void handleRefresh(TerminalMsgReceiveEW ew, MessageContext hc, MessageI req, ResponseI res) {
		SessionGd login = this.getSession(ew, true);

		NodeQueryOperationI<Expectation> finder = this.dataService.prepareNodeQuery(Expectation.class);

		// finder.addBeforeInterceptor(null);//TODO

		finder.propertyEq(Expectation.ACCOUNT_ID, login.getAccountId());
		NodeQueryResultI<Expectation> rst = finder.execute().getResult().assertNoError();

		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(rst.list());

		// additional fields;TODO use snapshot
		for (PropertiesI<Object> pts : el) {
			String id = (String) pts.getProperty(NodeI.PK_ID, true);//
		}
		res.setPayload("userExpList", el);
	}

	// TODO how to process the already confirmed request.
	protected List<PropertiesI<Object>> getCooperRequestList(String expId2) {
		// TODO query the count of request
		NodeQueryOperationI<ConnectRequest> crq = this.dataService.prepareNodeQuery(ConnectRequest.class);
		// crq.first(0);
		// crq.maxSize(10);//TODO limit from
		crq.propertyEq(ConnectRequest.EXP_ID2, expId2);//
		NodeQueryResultI<ConnectRequest> rst = crq.execute().getResult().assertNoError();
		List<PropertiesI<Object>> rt = NodeWrapperUtil.convert(rst.list());
		return rt;

	}
}
