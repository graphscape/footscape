/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.dataservice.expapp.impl.elastic;

import java.util.Date;
import java.util.List;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.support.VoidResult;
import com.fs.dataservice.api.expapp.operations.UserSnapshotOperationI;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper2.UserSnapshot;
import com.fs.dataservice.expapp.impl.elastic.support.ElasticOperationSupport;

/**
 * @author wu
 * 
 */
public class UserSnapshotOperationE extends
		ElasticOperationSupport<UserSnapshotOperationI, VoidResultI> implements
		UserSnapshotOperationI {

	/**
	 * @param rst
	 */
	public UserSnapshotOperationE(DataServiceI ds) {
		super(new VoidResult(ds));
	}

	/*
	 * Dec 4, 2012
	 */
	// TODO make sure user operation not take place in one mis-second
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		String accId = (String) this.parameters.getProperty(PK_ACCOUNTID, true);
		UserSnapshot old = this.dataService.getNewest(UserSnapshot.class,
				UserSnapshot.PK_ACCOUNT_ID, accId, false);
		Date from = old == null ? new Date(0) : old.getTimestamp();
		Date to = new Date();// now
		String crIdCsv = this.getCooperRequestIdCsv(accId, from, to);
		String expIdCsv = this.getExpIdCsv(accId,from,to);
		// Query cooper request from the last time

		UserSnapshot us = new UserSnapshot().forCreate(this.dataService);
		us.setAccountId(accId);
		us.setProperty(UserSnapshot.PK_ACTIVITY_ID_CSV, "");//

		us.setProperty(UserSnapshot.PK_COOPER_REQUEST_ID_CSV, crIdCsv);
		us.setProperty(UserSnapshot.PK_EXP_ID_CSV, expIdCsv);
		// TODO query cooper confirm,to filter the request.

	}

	
	//TODO ExpectionClose to filter this
	
	protected String getExpIdCsv(String accId, Date from, Date to) {
		NodeQueryOperationI<Expectation> crq = this.dataService
				.prepareNodeQuery(Expectation.class);
		crq.propertyEq(Expectation.ACCOUNT_ID, accId);//
		crq.timestampRange(from, false,to,true);//NOTE 

		NodeQueryResultI<Expectation> crrst = crq.execute().getResult()
				.assertNoError();
		String csv = "";
		List<Expectation> crL = crrst.list();
		for (int i = 0; i < crL.size(); i++) {
			Expectation cr = crL.get(i);
			csv += cr.getId();
			if (i != crL.size() - 1) {
				csv += ",";
			}
		}
		
		return csv;
	}

	protected String getCooperRequestIdCsv(String accId, Date from, Date to) {
		NodeQueryOperationI<CooperRequest> crq = this.dataService
				.prepareNodeQuery(CooperRequest.class);
		crq.propertyEq(CooperRequest.EXP_ID2, accId);
		crq.timestampRange(from,false, to,true);

		NodeQueryResultI<CooperRequest> crrst = crq.execute().getResult()
				.assertNoError();
		String csv = "";
		List<CooperRequest> crL = crrst.list();
		for (int i = 0; i < crL.size(); i++) {
			CooperRequest cr = crL.get(i);
			csv += cr.getId();
			if (i != crL.size() - 1) {
				csv += ",";
			}

		}
		return csv;
	}

	/*
	 * Dec 4, 2012
	 */
	@Override
	public UserSnapshotOperationI accountId(String accId) {
		this.parameter(UserSnapshotOperationI.PK_ACCOUNTID, accId);
		return this;
	}

}
