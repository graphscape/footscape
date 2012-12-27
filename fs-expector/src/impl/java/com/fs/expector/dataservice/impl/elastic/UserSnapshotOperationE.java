/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.expector.dataservice.impl.elastic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.ListUtil;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.operations.UserSnapshotOperationI;
import com.fs.expector.dataservice.api.result.UserSnapshotResultI;
import com.fs.expector.dataservice.api.wrapper.CooperConfirm;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper2.UserActivity;
import com.fs.expector.dataservice.api.wrapper2.UserSnapshot;
import com.fs.expector.dataservice.impl.elastic.support.ElasticOperationSupport;
import com.fs.expector.dataservice.impl.result.UserSnapshotResultImpl;

/**
 * @author wu
 * 
 */
public class UserSnapshotOperationE extends
		ElasticOperationSupport<UserSnapshotOperationI, UserSnapshotResultI>
		implements UserSnapshotOperationI {

	/**
	 * @param rst
	 */
	public UserSnapshotOperationE(DataServiceI ds) {
		super(new UserSnapshotResultImpl(ds));
	}

	/*
	 * Dec 4, 2012
	 */
	// TODO make sure user operation not take place in one mis-second
	@Override
	protected void executeInternal(UserSnapshotResultI rst) throws Exception {
		String accId = (String) this.parameters.getProperty(PK_ACCOUNTID, true);
		UserSnapshot old = this.dataService.getNewest(UserSnapshot.class,
				UserSnapshot.PK_ACCOUNT_ID, accId, false);
		Date from = old == null ? new Date(0) : old.getTimestamp();
		Date to = new Date();// now
		List<String> expIdL = old == null ? new ArrayList<String>()
				: new ArrayList<String>(old.getExpIdList());
		
		List<String> actIdL = old == null ? new ArrayList<String>()
				: new ArrayList<String>(old.getActivityIdList());

		List<String> newExpIdL = this.getExpIdList(accId, from, to);

		List<String> crIdL = this.processRequestId(accId, old, from, to);

		List<String> newActIdL = this.getActivityIdList(accId, from, to);
		// Query cooper request from the last time
		expIdL.addAll(newExpIdL);// TODO exp close operation.
		actIdL.addAll(newActIdL);// TODO activity close operation.
		//
		String expIdCsv = ListUtil.listToCsv(expIdL);
		String crIdCsv = ListUtil.listToCsv(crIdL);
		String actIdCsv = ListUtil.listToCsv(actIdL);

		//
		UserSnapshot us = new UserSnapshot().forCreate(this.dataService);
		us.setAccountId(accId);
		us.setProperty(UserSnapshot.PK_ACTIVITY_ID_CSV, actIdCsv);// activities
																	// open
		// state

		us.setProperty(UserSnapshot.PK_EXP_ID_CSV, expIdCsv);// exps in open

		us.setProperty(UserSnapshot.PK_COOPER_REQUEST_ID_CSV, crIdCsv);// cooper
																		// request
		// TODO query cooper confirm,to filter the request.
		us.save(this.getIsRefreshForSave());
		rst.set(us.getUniqueId());//

	}

	// TODO ExpectionClose to filter this

	protected List<String> getActivityIdList(String accId, Date from, Date to) {
		NodeQueryOperationI<UserActivity> crq = this.dataService
				.prepareNodeQuery(UserActivity.class);
		crq.propertyEq(UserActivity.PK_ACCOUNT_ID, accId);//
		crq.timestampRange(from, false, to, true);// NOTE

		NodeQueryResultI<UserActivity> crrst = crq.execute().getResult()
				.assertNoError();
		List<UserActivity> crL = crrst.list();
		return NodeWrapperUtil.getFieldList(crL, UserActivity.PK_ACTIVITY_ID);
	}

	protected List<String> getExpIdList(String accId, Date from, Date to) {
		NodeQueryOperationI<Expectation> crq = this.dataService
				.prepareNodeQuery(Expectation.class);
		crq.propertyEq(Expectation.ACCOUNT_ID, accId);//
		crq.timestampRange(from, false, to, true);// NOTE

		NodeQueryResultI<Expectation> crrst = crq.execute().getResult()
				.assertNoError();
		List<Expectation> crL = crrst.list();
		return NodeWrapperUtil.getIdList(crL);
	}

	protected List<String> processRequestId(String accId2, UserSnapshot old,
			Date from, Date to) {

		List<String> oldL = old == null ? new ArrayList<String>()
				: new ArrayList<String>(old.getCooperRequestIdList());
		List<String> newL = this.getCooperRequestIdList(accId2, from, to);
		List<String> confirmed = this.getCooperRequestIdConfirmedList(accId2,
				from, to);
		return ListUtil.minus(ListUtil.merge(oldL, newL), confirmed);
	}

	protected List<String> processRequestId(List<String> old,
			List<String> newL, List<String> confirmed) {

		List<String> rt = ListUtil.merge(old, newL);
		rt = ListUtil.minus(rt, confirmed);

		return rt;
	}

	/**
	 * the request to the account,for his/her confirm Dec 6, 2012
	 */
	protected List<String> getCooperRequestIdList(String accId2, Date from,
			Date to) {
		NodeQueryOperationI<CooperRequest> crq = this.dataService
				.prepareNodeQuery(CooperRequest.class);
		crq.propertyEq(CooperRequest.ACCOUNT_ID2, accId2);
		crq.timestampRange(from, false, to, true);

		NodeQueryResultI<CooperRequest> crrst = crq.execute().getResult()
				.assertNoError();
		return NodeWrapperUtil.getIdList(crrst.list());
	}

	protected List<String> getCooperRequestIdConfirmedList(String accId2,
			Date from, Date to) {
		NodeQueryOperationI<CooperConfirm> ccq = this.dataService
				.prepareNodeQuery(CooperConfirm.class);
		ccq.propertyEq(CooperConfirm.ACCOUNT_ID, accId2);
		ccq.timestampRange(from, false, to, true);

		NodeQueryResultI<CooperConfirm> crrst = ccq.execute().getResult()
				.assertNoError();

		return NodeWrapperUtil.getFieldList(crrst.list(),
				CooperConfirm.COOPER_REQUEST_ID);
	}

	/*
	 * Dec 4, 2012
	 */
	@Override
	public UserSnapshotOperationI accountId(String accId) {
		this.parameter(UserSnapshotOperationI.PK_ACCOUNTID, accId);
		return this;
	}

	public boolean getIsRefreshForSave() {
		return this.parameters.getPropertyAsBoolean(PK_REFRESH_FOR_SAVE, false);
	}

	/*
	 * Dec 6, 2012
	 */
	@Override
	public UserSnapshotOperationI refreshForSave(boolean ref) {
		//
		this.parameter(UserSnapshotOperationI.PK_REFRESH_FOR_SAVE, ref);
		return this;
	}

}
