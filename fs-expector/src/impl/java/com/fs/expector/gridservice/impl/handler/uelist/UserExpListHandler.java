/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.expector.gridservice.impl.handler.uelist;

import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;

/**
 * @author wu
 * 
 */
public class UserExpListHandler extends ExpectorTMREHSupport {
	@Handle("init")
	public void handleInit(HandleContextI hc, ResponseI res) {

	}

	/**
	 * get the detail of exp: the activity related.
	 * <p>
	 * Nov 28, 2012
	 */
	@Handle("get")
	public void handleGet(HandleContextI hc, RequestI req, ResponseI res) {
		String expId = (String) req.getPayload("expId");
		Expectation exp = this.dataService.getNewestById(Expectation.class,
				expId, false);
		NodeQueryOperationI<ExpActivity> nqo = this.dataService
				.prepareNodeQuery(ExpActivity.class);
		// TODO add loginId,add interceptor?

	}

	/**
	 * Refresh the summary list of ue. Nov 28, 2012
	 */
	@Handle("refresh")
	public void handleRefresh(HandleContextI hc, RequestI req, ResponseI res) {
		SessionGd login = this.getSession(hc, true);

		NodeQueryOperationI<Expectation> finder = this.dataService
				.prepareNodeQuery(Expectation.class);

		// finder.addBeforeInterceptor(null);//TODO

		finder.propertyEq(Expectation.ACCOUNT_ID, login.getAccountId());
		NodeQueryResultI<Expectation> rst = finder.execute().getResult()
				.assertNoError();

		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(rst.list());

		// additional fields;TODO use snapshot
		for (PropertiesI<Object> pts : el) {
			String id = (String) pts.getProperty(NodeI.PK_ID, true);//
			// act
			ExpActivity act = this.getExpActivity(id);
			if (act != null) {
				pts.setProperty("activityId", act.getActivityId());
			}
			// req
			List<PropertiesI<Object>> crL = this.getCooperRequestList(id);
			pts.setProperty("cooperRequestList", crL);

		}
		res.setPayload("userExpList", el);
	}

	protected ExpActivity getExpActivity(String expId) {
		ExpActivity rt = this.dataService.getNewest(ExpActivity.class,
				ExpActivity.PK_EXP_ID, expId, false);

		return rt;
	}

	// TODO how to process the already confirmed request.
	protected List<PropertiesI<Object>> getCooperRequestList(String expId2) {
		// TODO query the count of request
		NodeQueryOperationI<CooperRequest> crq = this.dataService
				.prepareNodeQuery(CooperRequest.class);
		// crq.first(0);
		// crq.maxSize(10);//TODO limit from
		crq.propertyEq(CooperRequest.EXP_ID2, expId2);//
		NodeQueryResultI<CooperRequest> rst = crq.execute().getResult()
				.assertNoError();
		List<PropertiesI<Object>> rt = NodeWrapperUtil.convert(rst.list());
		return rt;

	}
}
