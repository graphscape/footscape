/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.expector.dataservice.impl.elastic;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.dataservice.api.operations.CooperRequestOperationI;
import com.fs.expector.dataservice.api.result.CooperRequestResultI;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.impl.elastic.support.AuthedOperationSupport;
import com.fs.expector.dataservice.impl.result.CooperRequestResultImpl;

/**
 * @author wu
 * 
 */
public class CooperRequestOperationE extends
		AuthedOperationSupport<CooperRequestOperationI, CooperRequestResultI>
		implements CooperRequestOperationI {

	/**
	 * @param ds
	 */
	public CooperRequestOperationE(DataServiceI ds) {
		super(new CooperRequestResultImpl(ds));
	}

	@Override
	protected void executeInternal(Session login, CooperRequestResultI rst)
			throws Exception {
		CooperRequest cr = new CooperRequest().forCreate(this.dataService);
		cr.setAccountId1(login.getAccountId());
		cr.setExpId1((String) this.getParameter(EXPID1, true));
		//
		String expId2 = (String) this.getParameter(EXPID2, true);
		cr.setExpId2(expId2);

		Expectation exp2 = this.dataService.getNewestById(Expectation.class,
				expId2, true);
		cr.setAccountId2(exp2.getAccountId());
		cr.save(true);

		rst.setProperty(CooperRequestResultI.COOPER_REQUEST_ID, cr.getId());
	}

	/*
	 * Nov 4, 2012
	 */
	@Override
	public CooperRequestOperationI expId1(String expUid) {
		//
		this.parameter(EXPID1, expUid);

		return this;
	}

	/*
	 * Nov 4, 2012
	 */
	@Override
	public CooperRequestOperationI expId2(String expUid) {
		//
		this.parameter(EXPID2, expUid);
		return this;
	}

}