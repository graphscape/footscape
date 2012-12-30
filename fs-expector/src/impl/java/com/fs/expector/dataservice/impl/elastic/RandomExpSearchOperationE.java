/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.expector.dataservice.impl.elastic;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.core.impl.elastic.operations.NodeQueryOperationE;
import com.fs.expector.dataservice.api.operations.AuthedQueryOperationI;
import com.fs.expector.dataservice.api.operations.CooperConfirmOperationI;
import com.fs.expector.dataservice.api.operations.ExpSearchOperationI;
import com.fs.expector.dataservice.api.wrapper.Activity;
import com.fs.expector.dataservice.api.wrapper.CooperConfirm;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.dataservice.api.wrapper2.UserActivity;
import com.fs.expector.dataservice.impl.result.CooperConfirmResultImpl;

/**
 * @author wu
 * 
 */
public class RandomExpSearchOperationE extends NodeQueryOperationE<Expectation>
		implements ExpSearchOperationI {

	/**
	 * @param ds
	 */
	public RandomExpSearchOperationE(DataServiceI ds) {
		super(ds);
	}

	@Override
	public ExpSearchOperationI forExp(String expId) {
		//
		this.parameter(ExpSearchOperationI.PK_EXP_ID, expId);
		return this;
	}

	/*
	 * Nov 30, 2012
	 */
	@Override
	public ExpSearchOperationI keywords(String kws) {
		//
		this.parameter(ExpSearchOperationI.PK_KEYWORDS, kws);
		return this;
	}

	/*
	 *Nov 30, 2012
	 */
	@Override
	protected void executeInternal(NodeQueryResultI<Expectation> rst)
			throws Exception {
		// 
		this.nodeType(Expectation.class);
		//
		super.executeInternal(rst);
	}

}